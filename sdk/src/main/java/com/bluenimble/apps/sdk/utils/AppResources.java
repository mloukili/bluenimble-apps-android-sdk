package com.bluenimble.apps.sdk.utils;

import com.bluenimble.apps.sdk.Lang;

import android.R;
import android.content.Context;
import android.graphics.drawable.Drawable;

import java.lang.reflect.Field;

public class AppResources {

	@SuppressWarnings("deprecation")
	public static Drawable drawable (Context context, String name) {
		return context.getResources ().getDrawable (id (name));
	}

	public static boolean exists (String name) {
		if (Lang.isNullOrEmpty (name)) {
			return false;
		}
		name = name.trim ();
		if (name.indexOf (Lang.DOT) >= 0) {
			name = name.substring (0, name.indexOf (Lang.DOT));
		}
		try {
			Field field = R.drawable.class.getField (name);
			return true;
		} catch (Exception e) {
			// TODO: log style -> warning
			return false;
		}
	}

	public static int id (String name) {
		if (Lang.isNullOrEmpty (name)) {
			return 0;
		}
		name = name.trim ();
		if (name.contains (Lang.DOT)) {
			name = name.substring (0, name.indexOf (Lang.DOT));
		}
		try {
			Field field = R.drawable.class.getField (name);
			return (int) field.get (null);
		} catch (Exception e) {
			// TODO: log style -> warning
			//e.printStackTrace ();
			return 0;
		}
	}

	public static int id (String name, String packageName) {
		if (Lang.isNullOrEmpty (name)) {
			return 0;
		}
		name = name.trim ();
		if (name.contains (Lang.DOT)) {
			name = name.substring (0, name.indexOf (Lang.DOT));
		}
		try {
			Class<?> clazz = Class.forName (packageName + ".R$drawable");
			Field field = clazz.getField (name);
			return (int) field.get (null);
		} catch (Exception e) {
			// TODO: log style -> warning
			//e.printStackTrace ();
			return 0;
		}
	}
	
}
