package com.bluenimble.apps.sdk.controller.impls;

import com.bluenimble.apps.sdk.controller.DataHolder;
import com.bluenimble.apps.sdk.spec.ApplicationSpec;
import com.bluenimble.apps.sdk.spec.BindingSpec;

public class AgnosticDataHolder implements DataHolder  {

	private static final long serialVersionUID = 713158374746493539L;
	
	public static final DataHolder Instance = new AgnosticDataHolder ();
	
	@Override
	public Exception exception () {
		return null;
	}

	@Override
	public Object get (String namespace, String... property) {
		return null;
	}

	@Override
	public void set (String namespace, Object value, String... property) {
	}

	@Override
	public void exception (Exception exception) {
	}

	@Override
	public String resolve (String ns, String name) {
		return null;
	}

	@Override
	public Object valueOf (ApplicationSpec applicationSpec, BindingSpec bindingSpec) {
		return null;
	}
	
}