/*
 * Copyright (C) 2009 BRAVEMOUNT IT SOLUTIONS PVT. LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * ClassName - Preferences
 * Usage - To save/get preference values in shared preferences
 */

package com.angelhack;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
	public static String PREF_FILE_NAME;
	private Context con;

	public Preferences(Context curCon, String prefFile) {
		con = curCon;
		PREF_FILE_NAME = prefFile;
	}

	/*
	 * @name is name of the preference
	 * 
	 * @value is string value for the preference Saving Name - value pairs into
	 * preference
	 */
	public void saveValueIntoPreference(String name, String value) {
		SharedPreferences preferences = con.getSharedPreferences(
				PREF_FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(name, value);
		editor.commit();
	}

	public void saveValueIntoPreference(String name, Boolean value) {
		SharedPreferences preferences = con.getSharedPreferences(
				PREF_FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(name, value);
		editor.commit();
	}

	/*
	 * @name is name of the preference
	 * 
	 * @value is int value for the preference Saving Name - value pairs into
	 * preference
	 */
	public void saveValueIntoPreference(String name, int value) {
		SharedPreferences preferences = con.getSharedPreferences(
				PREF_FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(name, value);
		editor.commit();
	}

	/*
	 * @name is name of the preference
	 * 
	 * @value is float value for the preference Saving Name - value pairs into
	 * preference
	 */
	public void saveValueIntoPreference(String name, float value) {
		SharedPreferences preferences = con.getSharedPreferences(
				PREF_FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putFloat(name, value);
		editor.commit();
	}

	/*
	 * @name is name of the preference
	 * 
	 * @value is float value for the preference Saving Name - value pairs into
	 * preference
	 */
	public void saveValueIntoPreference(String name, long value) {
		SharedPreferences preferences = con.getSharedPreferences(
				PREF_FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putLong(name, value);
		editor.commit();
	}

	/*
	 * Retrieving the value from preference by name
	 * 
	 * @name is name of the preference
	 * 
	 * @value is int value for the preference *
	 */
	public int getValueFromPreference(String name, int value) {
		SharedPreferences preferences = con.getSharedPreferences(
				PREF_FILE_NAME, Context.MODE_PRIVATE);
		return preferences.getInt(name, value);
	}

	/*
	 * Retrieving the value from preference by name
	 * 
	 * @name is name of the preference
	 * 
	 * @value is String value for the preference *
	 */
	public String getValueFromPreference(String name, String value) {
		SharedPreferences preferences = con.getSharedPreferences(
				PREF_FILE_NAME, Context.MODE_PRIVATE);
		return preferences.getString(name, value);
	}

	/*
	 * Retrieving the value from preference by name
	 * 
	 * @name is name of the preference
	 * 
	 * @value is Float value for the preference *
	 */
	public float getValueFromPreference(String name, float value) {
		SharedPreferences preferences = con.getSharedPreferences(
				PREF_FILE_NAME, Context.MODE_PRIVATE);
		float fl = preferences.getFloat(name, value);
		return fl;
	}

	public boolean getValueFromPreference(String name, Boolean value) {
		SharedPreferences preferences = con.getSharedPreferences(
				PREF_FILE_NAME, Context.MODE_PRIVATE);
		boolean fl = preferences.getBoolean(name, value);
		return fl;
	}

	/*
	 * Retrieving the value from preference by name
	 * 
	 * @name is name of the preference
	 * 
	 * @value is Float value for the preference *
	 */
	public long getValueFromPreference(String name, long value) {
		SharedPreferences preferences = con.getSharedPreferences(
				PREF_FILE_NAME, Context.MODE_PRIVATE);
		long fl = preferences.getLong(name, value);
		return fl;
	}

	/*
	 * Checking the value from preference by name
	 * 
	 * @name is name of the preference
	 * 
	 * @value is String value for the preference *
	 */
	public boolean checkPreference(String Name, String Value) {
		String retValue = getValueFromPreference(Name, Value);
		if (retValue == Value) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Checking the value from preference by name
	 * 
	 * @name is name of the preference
	 * 
	 * @value is String value for the preference *
	 */
	public boolean checkPreference(String Name, int Value) {
		int retValue = getValueFromPreference(Name, Value);
		if (retValue == Value) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Checking the value from preference by name
	 * 
	 * @name is name of the preference
	 * 
	 * @value is float value for the preference *
	 */
	public boolean checkPreference(String Name, float Value) {
		float retValue = getValueFromPreference(Name, Value);
		if (retValue == Value) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Save the value into preference by name if not exists
	 * 
	 * @name is name of the preference
	 * 
	 * @value is String value for the preference
	 */
	public String savePreference(String Name, String Value) {
		if (!checkPreference(Name, "")) {
			saveValueIntoPreference(Name, Value);
			return "success";
		} else {
			return "Already exists";
		}
	}

	/*
	 * Save the value into preference by name if not exists
	 * 
	 * @name is name of the preference
	 * 
	 * @value is int value for the preference
	 */
	public String savePreference(String Name, int Value) {
		if (!checkPreference(Name, 0)) {
			saveValueIntoPreference(Name, Value);
			return "success";
		} else {
			return "Already exists";
		}
	}

	/*
	 * Save the value into preference by name if not exists
	 * 
	 * @name is name of the preference
	 * 
	 * @value is float value for the preference
	 */
	public String savePreference(String Name, float Value) {
		if (!checkPreference(Name, (float) 0)) {
			saveValueIntoPreference(Name, Value);
			return "success";
		} else {
			return "Already exists";
		}
	}

	/*
	 * Save the value into preference by name if not exists
	 * 
	 * @name is name of the preference
	 * 
	 * @value is float value for the preference
	 */
	public String savePreference(String Name, long Value) {
		if (!checkPreference(Name, (long) 0)) {
			saveValueIntoPreference(Name, Value);
			return "success";
		} else {
			return "Already exists";
		}
	}

	/*
	 * Remove preference by name
	 * 
	 * @name is name of the preference
	 */
	public void removePreference(String name) {
		SharedPreferences preferences = con.getSharedPreferences(
				PREF_FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.remove(name);
		editor.commit();
	}

	/*
	 * Remove all preference
	 */
	public void removeAllPreference() {
		SharedPreferences preferences = con.getSharedPreferences(
				PREF_FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.clear();
		editor.commit();
	}

}
