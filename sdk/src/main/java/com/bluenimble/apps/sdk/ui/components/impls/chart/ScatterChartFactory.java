package com.bluenimble.apps.sdk.ui.components.impls.chart;

import java.util.ArrayList;
import java.util.List;

import com.bluenimble.apps.sdk.Json;
import com.bluenimble.apps.sdk.application.UIActivity;
import com.bluenimble.apps.sdk.controller.DataHolder;
import com.bluenimble.apps.sdk.json.JsonArray;
import com.bluenimble.apps.sdk.json.JsonObject;
import com.bluenimble.apps.sdk.spec.ApplicationSpec;
import com.bluenimble.apps.sdk.spec.BindingSpec;
import com.bluenimble.apps.sdk.spec.ComponentSpec;
import com.bluenimble.apps.sdk.spec.LayerSpec;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

public class ScatterChartFactory extends ChartFactory {

	private static final long serialVersionUID = 8437367345924192857L;
	
	private static final String Id = "chart.scatter";

	public ScatterChartFactory () {
		// supportEvent (EventListener.Event.check);
	}

	@Override
	public String id () {
		return Id;
	}
	
	@Override
	public View create (UIActivity activity, ViewGroup group, LayerSpec layer, ComponentSpec spec, DataHolder dh) {
		
		style = (JsonObject)spec.style ().get (Style.scatter.class.getSimpleName ());

		ScatterChart chart = new ScatterChart (activity);
		
		// animation
		int animate = Json.getInteger (style, Style.Animate, 0);
		if (animate > 0) {
			chart.animateY (animate);
		}
		
		return applyStyle (group, chart, spec, dh);
	}

	/**
	 * Data Model:
	 * 		JsonArray
	 * 			JsonObject [multiple series] 
	 * 				title
	 * 				JsonArray values
	 **/
	@Override
	public void bind (ComponentSpec.Binding binding, View view, ApplicationSpec applicationSpec, ComponentSpec spec, DataHolder dh) {
		
		if (view == null || !(view instanceof ScatterChart)) {
			// TODO: log
			return;
		}
		
		BindingSpec bindingSpec = spec.binding (binding);
		if (bindingSpec == null) {
			return;
		}
		
		ScatterChart chart = (ScatterChart)view;
		
		switch (binding) {
			case Set:
				if (dh == null) {
					chart.clear ();
					return;
				}
				Object value = dh.valueOf (applicationSpec, bindingSpec);
				if (value == null) {
					return;
				}
				
				if (!(value instanceof JsonArray)) {
					// TODO: log
					return;
				}
				
				JsonArray array = (JsonArray)value;
				if (array.isEmpty ()) {
					// TODO: log
					return;
				}

				ScatterData data = chart.getData ();
				if (data == null) {
					data = new ScatterData ();
					chart.setData (data);
				}

				for (int i = 0; i < array.count (); i++) {
					JsonObject series = (JsonObject)array.get (i);
					if (Json.isNullOrEmpty (series)) {
						continue;
					}
					JsonArray values = Json.getArray (series, Record.Values);
					if (values == null || values.isEmpty ()) {
						continue;
					}
					
					List<Entry> entries = new ArrayList<Entry> ();
					for (int j = 0; j < values.count (); j++) {
						JsonArray record = (JsonArray)values.get (j);
						entries.add (new Entry (Float.valueOf ((String)record.get (0)), Float.valueOf ((String)record.get (1))));
					}
					data.addDataSet ( new ScatterDataSet (entries, Json.getString (series, Custom.Title)) );
				}

				chart.invalidate (); // refresh

				break;
			default:
				break;
		}
		
	}

	@Override
	public void addEvent (UIActivity activity, View view, ApplicationSpec applicationSpec, ComponentSpec component, String eventName, JsonObject eventSpec) {
		if (view == null || !(view instanceof RadioGroup)) {
			// TODO: log
			return;
		}
		
		if (!isEventSupported (eventName)) {
			// TODO: log
			return;
		}
		
		// attach event
		
	}
	
}
