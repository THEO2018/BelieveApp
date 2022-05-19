package com.netset.believeapp.Utils

import android.content.Context
import android.content.SharedPreferences

object PrefUtils {
    const val Old_DATA="OLD_DATA"
    fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("LINKODES_PREF", Context.MODE_PRIVATE)
    }


    fun storeAuthKey(context: Context, apiKey: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString("AUTH_KEY", apiKey)
        editor.apply()
    }

    fun getAuthKey(context: Context): String? {
        return getSharedPreferences(context).getString("AUTH_KEY", null)
    }

    fun setSaveValue(context: Context, key: String, value: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(key, value)
        editor.apply()
        editor.commit()
    }

    fun getSaveValue(context: Context, key: String): String {
        if (getSharedPreferences(context).getString(key, "")!!.isEmpty()) {
            return ""
        } else {
            return getSharedPreferences(context).getString(key, "")!!
        }
    }

    fun getSaveIntValue(context: Context, key: String): Int {
        return getSharedPreferences(context).getInt(key, 0)
    }


    fun getUserLoggedInStatus(context: Context): Boolean? {
        return getSharedPreferences(context).getBoolean("IS_USER_LOGGED_IN", false)
    }

    fun clearPrefs(context: Context) {
        val sharedPrefs = context.getSharedPreferences("LINKODES_PREF", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.clear()
        editor.apply()
    }

    fun clear(context: Context, name: String) {
        val sharedPrefs = context.getSharedPreferences("LINKODES_PREF", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.remove(name)
        editor.apply()
    }

}