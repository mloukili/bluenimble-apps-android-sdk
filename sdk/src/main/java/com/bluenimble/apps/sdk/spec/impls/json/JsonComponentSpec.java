package com.bluenimble.apps.sdk.spec.impls.json;

import com.bluenimble.apps.sdk.Json;
import com.bluenimble.apps.sdk.Lang;
import com.bluenimble.apps.sdk.Spec;
import com.bluenimble.apps.sdk.controller.DataHolder;
import com.bluenimble.apps.sdk.json.JsonObject;
import com.bluenimble.apps.sdk.spec.BindingSpec;
import com.bluenimble.apps.sdk.spec.ComponentSpec;
import com.bluenimble.apps.sdk.spec.StyleSpec;
import com.bluenimble.apps.sdk.spec.ThemeSpec;
import com.bluenimble.apps.sdk.ui.components.ComponentsRegistry;
import com.bluenimble.apps.sdk.ui.themes.impls.JsonStyleSpec;

public class JsonComponentSpec extends JsonEventAwareSpec implements ComponentSpec {

	private static final long serialVersionUID = 6704442381425657398L;
	
	protected JsonObject spec;
	
	private BindingSpec bindingSet;
	private BindingSpec bindingGet;
	
	private StyleSpec 	style;
	
	public JsonComponentSpec (JsonObject spec, ThemeSpec appTheme) {
		super (Json.getObject (spec, Spec.Events));
		this.spec = spec;
		
		String id = id ();
		if (Lang.isNullOrEmpty (id)) {
			spec.set (Spec.page.layer.component.Id, Lang.UUID (8));
		}
		
		JsonObject oBinding = Json.getObject (spec, Spec.page.layer.component.binding.class.getSimpleName ());
		if (oBinding == null) {
			oBinding = new JsonObject ();
		} 
		bindingSet 	= binding (oBinding, Spec.page.layer.component.binding.Set);
		bindingGet 	= binding (oBinding, Spec.page.layer.component.binding.Get);
		
		String [] styles = Lang.split (Json.getString (spec, Spec.page.layer.component.Style), Lang.BLANK, true);
		
		style = new JsonStyleSpec (appTheme, Lang.add (new String [] { Lang.STAR, type (), id () }, styles));
		
	}

	@Override
	public String id () {
		return Json.getString (spec, Spec.page.layer.component.Id);
	}

	@Override
	public String type () {
		return Json.getString (spec, Spec.page.layer.component.Type, ComponentsRegistry.Default.Text);
	}

	@Override
	public Object get (String name) {
		return Json.find (spec, Spec.page.layer.component.Custom, name);
	}

	@Override
	public StyleSpec style () {
		return style;
	}

	@Override
	public BindingSpec binding (Binding binding) {
		switch (binding) {
			case Set:
				return bindingSet;
			case Get:
				return bindingGet;
			default:
				break;
		}
		return null;
	}
	
	// if no binding set/get found, a default one is created with the component id 
	private BindingSpec binding (JsonObject oBinding, String key) {
		
		JsonObject oBindingByKey = null;
		
		Object bindingByKey = oBinding.get (key);
		if (bindingByKey instanceof String) {
			String sBindingByKey = (String) bindingByKey;
			oBindingByKey = new JsonObject ();
			int indexOfDot = sBindingByKey.indexOf (Lang.DOT);
			if (indexOfDot > 0) {
				oBindingByKey.set (Spec.page.layer.component.binding.Source, sBindingByKey.substring (0, indexOfDot));
				oBindingByKey.set (Spec.page.layer.component.binding.Property, sBindingByKey.substring (indexOfDot + 1));
			} else {
				oBindingByKey.set (Spec.page.layer.component.binding.Property, sBindingByKey);
			}
		} else if (bindingByKey instanceof JsonObject) {
			oBindingByKey = (JsonObject)bindingByKey;
		}
		
		if ((oBindingByKey == null || oBindingByKey.isEmpty ()) && Spec.page.layer.component.binding.Get.equals (key)) {
			if (Lang.isNullOrEmpty (id ())) {
				return null;
			}
			oBindingByKey = (JsonObject)new JsonObject ().set (Spec.page.layer.component.binding.Source, DataHolder.Namespace.App).set (Spec.page.layer.component.binding.Property, id ());
		}
		if (oBindingByKey == null) {
			return null;
		}
		return new JsonBindingSpec (oBindingByKey);
	}
	
}