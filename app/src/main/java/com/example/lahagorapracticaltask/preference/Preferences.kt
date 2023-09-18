package com.example.lahagorapracticaltask.preference

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.lahagorapracticaltask.utils.Constants

class Preferences(context: Context?) {
    private val sharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor
    fun getInt(key: String?, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun getInt(key: String?): Int {
        return sharedPreferences.getInt(key, 0)
    }

    fun putInt(key: String?, value: Int?) {
        editor.putInt(key, value!!)
        editor.commit()
    }

    fun getString(key: String?): String? {
        return sharedPreferences.getString(key, "")
    }

    fun getString(key: String?, defaultValue: String?): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun putString(key: String?, value: String?) {
        editor.putString(key, value)
        editor.commit()
    }

    fun getBoolean(key: String?): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun getBoolean(key: String?, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun putBoolean(key: String?, value: Boolean) {
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun putSkill(value: String) {
        putString(Constants.SKILL, value)
    }

    fun getSkill(): String {
        return getString(Constants.SKILL, "0").toString()
    }

    fun putStyle(value: String) {
        putString(Constants.STYLE, value)
    }

    fun getSeries(): String {
        return getString(Constants.SERIES, "0").toString()
    }

    fun putSeries(value: String) {
        putString(Constants.SERIES, value)
    }

    fun getStyle(): String {
        return getString(Constants.STYLE, "0").toString()
    }

    fun putCirriculum(value: String) {
        putString(Constants.CIRRICULUM, value)
    }

    fun getCirriculum(): String {
        return getString(Constants.CIRRICULUM, "0").toString()
    }

    fun putEducator(value: String) {
        putString(Constants.EDUCATOR, value)
    }

    fun getEducator(): String {
        return getString(Constants.EDUCATOR, "0").toString()
    }

    fun putOwned(value: String) {
        putString(Constants.PREF_ONLY_SHOW_OWNED, value)
    }

    fun getOwned(): String {
        return getString(Constants.PREF_ONLY_SHOW_OWNED, "0").toString()
    }


    fun remove(key: String?) {
        editor.remove(key)
        editor.commit()
    }

    fun clear() {
        editor.clear()
        editor.apply()
    }


    init {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        editor = sharedPreferences.edit()
    }


}