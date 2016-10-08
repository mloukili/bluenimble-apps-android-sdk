package com.bluenimble.apps.sdk.application;

import com.bluenimble.apps.sdk.Spec;
import com.bluenimble.apps.sdk.controller.ActionProcessor;
import com.bluenimble.apps.sdk.controller.DataHolder;
import com.bluenimble.apps.sdk.json.JsonObject;
import com.bluenimble.apps.sdk.spec.ApplicationSpec;
import com.bluenimble.apps.sdk.spec.LayerSpec;
import com.bluenimble.apps.sdk.ui.effects.impls.BindEffect;
import com.bluenimble.apps.sdk.ui.renderer.impls.DefaultRenderer.LifeCycleEvent;
import com.bluenimble.apps.sdk.ui.utils.SpecHelper;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UILayer extends Fragment {
		
	public interface Exchange {
		String Event 	= "event";
		String Data 	= "data";
		String Layer 	= "layer";
	}
	
	private 	JsonObject 	createEvent;
	private 	DataHolder 	dh;
	
	protected 	LayerSpec 	layer;

	public UILayer () {
	}

	public static UILayer create (LayerSpec layer, DataHolder dh) {

		UILayer fragment = new UILayer ();

		fragment.setRetainInstance (true);

		fragment.layer 	= layer;
		fragment.dh 	= dh;

		return fragment;

	}
	
	@Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle state) {
		UIActivity activity = (UIActivity)getActivity ();
		
		ApplicationSpec application = ((UIApplication)activity.getApplication ()).getSpec ();
		
		return application.renderer ().render (application, layer, null, container, activity);
    }
	
	@Override
    public void onViewCreated (View container, Bundle state) {
		
		UIActivity activity = (UIActivity)getActivity ();
		ApplicationSpec application = ((UIApplication)activity.getApplication ()).getSpec ();

		SpecHelper.fireCreateEvent (layer, activity, activity.root (), dh);

    }
	
}
