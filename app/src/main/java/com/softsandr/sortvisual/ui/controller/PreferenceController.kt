package com.softsandr.sortvisual.ui.controller

import android.content.SharedPreferences

class PreferenceController(private val sharedPreference: SharedPreferences) {

    fun saveStringToPreference(key: String, value: String?) {
        if (value == null) {
            sharedPreference.edit().remove(key).apply()
        } else {
            sharedPreference.edit().putString(key, value).apply()
        }
    }

    fun saveFloatToPreference(key: String, value: Float?) {
        if (value == null) {
            sharedPreference.edit().remove(key).apply()
        } else {
            sharedPreference.edit().putFloat(key, value).apply()
        }
    }

    fun saveIntToPreference(key: String, value: Int?) {
        if (value == null) {
            sharedPreference.edit().remove(key).apply()
        } else {
            sharedPreference.edit().putInt(key, value).apply()
        }
    }

    fun saveLongToPreference(key: String, value: Long?) {
        if (value == null) {
            sharedPreference.edit().remove(key).apply()
        } else {
            sharedPreference.edit().putLong(key, value).apply()
        }

    }

    fun saveBooleanToPreference(key: String, value: Boolean?) {
        if (value == null) {
            sharedPreference.edit().remove(key).apply()
        } else {
            sharedPreference.edit().putBoolean(key, value).apply()
        }
    }

    @JvmOverloads fun getStringFromPreference(key: String, defaultValue: String? = null): String {
        return sharedPreference.getString(key, defaultValue)
    }

    fun getFloatFromPreference(key: String): Float {
        return sharedPreference.getFloat(key, java.lang.Float.MIN_VALUE)
    }

    fun getIntFromPreference(key: String): Int {
        return sharedPreference.getInt(key, Integer.MIN_VALUE)
    }

    fun getIntFromPreference(key: String, defaultValue: Int): Int {
        return sharedPreference.getInt(key, defaultValue)
    }

    fun getLongFromPreference(key: String, defaultValue: Long): Long {
        return sharedPreference.getLong(key, defaultValue)
    }

    fun getBooleanFromPreference(key: String, defaultValue: Boolean): Boolean {
        return sharedPreference.getBoolean(key, defaultValue)
    }
}
