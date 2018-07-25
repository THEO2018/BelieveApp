package com.netset.believeapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class GeneralValues {
	static SharedPreferences preferences;
	static SharedPreferences.Editor editor;

	public static void set_user_id(Context activity, String uid) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putString("UID", uid);
		editor.commit();

	}

	public static String get_user_id(Context context) {
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		String user_id = preferences.getString("UID", "");
		return user_id;
	}


	public static void set_device_id(Context context, String uid) {
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		editor = preferences.edit();
		editor.putString("UID", uid);
		editor.commit();

	}

	public static String get_device_id(Context activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String user_id = preferences.getString("UID", "");
		return user_id;
	}
	public static void set_Access_Key(Context context, String uid) {
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		editor = preferences.edit();
		editor.putString("Api_Key", uid);
		editor.commit();

	}

	public static String get_Access_Key(Context activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String user_id = preferences.getString("Api_Key", "");
		return user_id;
	}


	public static void set_appVersion(Context activity, String token) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putString("version", token);
		editor.commit();

	}

	public static String get_appVersion(Context activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String count = preferences.getString("version", "");
		return count;
	}



	public static void set_notify_status(Activity activity, String bid) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putString("BID", bid);
		editor.commit();

	}

	public static String get_notify_status(Activity activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String book_id = preferences.getString("BID", "");
		return book_id;
	}

	public static void set_timezone(Context activity, String uid) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putString("user_name", uid);
		editor.commit();

	}

	public static String get_timezone(Context activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String user_id = preferences.getString("user_name", "");
		return user_id;
	}
	public static void set_email(Activity activity, String uid) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putString("email", uid);
		editor.commit();

	}

	public static String get_email(Activity activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String user_id = preferences.getString("email", "");
		return user_id;
	}
	public static void set_passwrd(Activity activity, String uid) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putString("passwrd", uid);
		editor.commit();

	}

	public static String get_passwrd(Activity activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String user_id = preferences.getString("passwrd", "");
		return user_id;
	}
	public static void set_last_name(Activity activity, String uid) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putString("last_name", uid);
		editor.commit();

	}

	public static String get_last_name(Activity activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String user_id = preferences.getString("last_name", "");
		return user_id;
	}

	public static void set_user_image(Activity activity, String uid) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putString("image", uid);
		editor.commit();

	}

	public static String get_user_image(Activity activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String user_id = preferences.getString("image", "");
		return user_id;
	}


	public static void set_current_lat(Activity activity, String uid) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putString("user_type", uid);
		editor.commit();

	}

	public static String get_current_lat(Activity activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String user_id = preferences.getString("user_type", "");
		return user_id;
	}

public static void set_gcmid(Activity activity, String uid) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putString("gcmid", uid);
		editor.commit();

	}

	public static String get_gcmid(Activity activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String user_id = preferences.getString("gcmid", "");
		return user_id;
	}



	public static void set_current_long(Activity activity, String uid) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putString("image", uid);
		editor.commit();

	}

	public static String get_current_long(Activity activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String user_id = preferences.getString("image", "");
		return user_id;
	}
	public static void set_loginbool(Context activity, Boolean rid) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putBoolean("bool", rid);
		editor.commit();

	}


	public static void set_logintype(Context activity, String type) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putString("Type", type);
		editor.commit();

	}

	public static String get_logintype(Context activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String user_id = preferences.getString("Type", "");
		return user_id;
	}

	public static void set_notifystatus(Activity activity, String type) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putString("status", type);
		editor.commit();

	}

	public static String get_notifystatus(Activity activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String user_id = preferences.getString("status", "");
		return user_id;
	}


	public static void set_notifybool(Activity activity, String type) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putString("status", type);
		editor.commit();

	}

	public static String get_notifybool(Activity activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String user_id = preferences.getString("status", "");
		return user_id;
	}






	public static Boolean get_loginbool(Activity activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Boolean user_id = preferences.getBoolean("bool", false);
		return user_id;
	}

	public static void set_signupbool(Activity activity, Boolean rid) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putBoolean("bool", rid);
		editor.commit();

	}

	public static Boolean get_signupbool(Activity activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Boolean user_id = preferences.getBoolean("bool", false);
		return user_id;
	}

	public static void set_notifycount(Activity activity, String uid) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putString("count", uid);
		editor.commit();

	}
	public static void set_overlaybool(Activity activity, Boolean rid) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putBoolean("bool", rid);
		editor.commit();

	}
	public static Boolean get_overlaybool(Activity activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Boolean user_id = preferences.getBoolean("bool", false);
		return user_id;
	}

	public static String get_notifycount(Activity activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String count = preferences.getString("count", "");
		return count;
	}

	public static void set_msgcount(Activity activity, String uid) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putString("count", uid);
		editor.commit();

	}

	public static String get_msgcount(Activity activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String count = preferences.getString("count", "");
		return count;
	}


	public static void set_notid(Activity activity, String uid) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putString("count", uid);
		editor.commit();

	}

	public static String get_notid(Activity activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String count = preferences.getString("count", "");
		return count;
	}

	public static void set_msgbool(Context activity, Boolean rid) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putBoolean("bool", rid);
		editor.commit();

	}

	public static Boolean get_msgbool(Context activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Boolean user_id = preferences.getBoolean("bool", false);
		return user_id;
	}


	public static void set_count(Context activity, String count) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putString("count", count);
		editor.commit();

	}

	public static String get_count(Context activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String count = preferences.getString("count", "");
		return count;
	}
	public static void set_pack(Context activity, String pack) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putString("count", pack);
		editor.commit();

	}

	public static String get_pack(Context activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String count = preferences.getString("count", "");
		return count;
	}

	public static void set_token(Context activity, String token) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putString("token", token);
		editor.commit();

	}

	public static String get_token(Context activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String count = preferences.getString("token", "");
		return count;
	}
	public static void set_profilebool(Activity activity, String type) {

		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = preferences.edit();
		editor.putString("status", type);
		editor.commit();

	}

	public static String get_profilebool(Activity activity) {
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String user_id = preferences.getString("status", "");
		return user_id;
	}








}
