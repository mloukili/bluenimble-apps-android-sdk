package com.bluenimble.apps.sdk.application;

import com.bluenimble.apps.sdk.Lang;
import com.bluenimble.apps.sdk.controller.DataHolder;
import com.bluenimble.apps.sdk.json.JsonObject;
import com.bluenimble.apps.sdk.spec.ApplicationSpec;
import com.bluenimble.apps.sdk.spec.LayerSpec;
import com.bluenimble.apps.sdk.ui.components.impls.listeners.EventListener;
import com.bluenimble.apps.sdk.utils.SpecHelper;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashSet;
import java.util.Set;

public class UILayer extends Fragment {
		
/*
	public interface Exchange {
		String Event 	= "event";
		String Data 	= "data";
	}
*/

	protected 	LayerSpec 	layer;
	protected 	DataHolder 	dh;

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
		
		return application.renderer ().render (application, layer, dh, container, activity);
    }
	
	@Override
    public void onViewCreated (View container, Bundle state) {
		
		UIActivity activity = (UIActivity)getActivity ();
		ApplicationSpec application = ((UIApplication)activity.getApplication ()).getSpec ();

		SpecHelper.fireCreateEvent (layer, layer.id (), activity, activity.root (), true, dh);

    }
	
}
