package com.bluenimble.apps.sdk.spec;

public interface ComponentSpec extends EventAwareSpec, StylishSpec {

	enum Binding {
		Set,
		Get
	}
	
	String 				id();
	String 				type();
	Object 				get(String name);
	
	BindingSpec			binding(Binding binding);

}