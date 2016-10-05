package com.bluenimble.apps.sdk.ui.components.impls.listeners;

import com.bluenimble.apps.sdk.application.UIActivity;
import com.bluenimble.apps.sdk.controller.ActionProcessor;
import com.bluenimble.apps.sdk.json.JsonObject;

import android.view.View;
import android.view.View.OnLongClickListener;

public class OnLongPressListenerImpl extends EventListener implements OnLongClickListener {
	
	private static final long serialVersionUID = 4426753434150177456L;

	public OnLongPressListenerImpl (Event event, JsonObject eventSpec) {
		super (event, eventSpec);
	}

	@Override
	public boolean onLongClick (View view) {
		ActionProcessor.process (event.name (), eventSpec, (UIActivity)view.getContext (), view, null);
		return true;
	}
	
}