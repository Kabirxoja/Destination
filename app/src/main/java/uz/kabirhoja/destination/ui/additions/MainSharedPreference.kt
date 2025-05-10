package uz.kabirhoja.destination.ui.additions

import android.content.Context

object MainSharedPreference {
    private const val PREF_NAME = "language_pref"

    private const val LANGUAGE_KEY = "selected_language"

    private const val PREF_JSON_VERSION = "json_version"

    private const val PREF_SPEAKER = "speaker_type"


    fun saveLanguage(context: Context, language: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(LANGUAGE_KEY, language).apply()
    }

    fun getLanguage(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(LANGUAGE_KEY, "uz") ?: "uz"
    }

    fun saveUpdateJsonVersion(context: Context, version: Int) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putInt(PREF_JSON_VERSION, version).apply()
    }

    fun getUpdateJsonVersion(context: Context): Int {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(PREF_JSON_VERSION, -1)
    }

    fun saveSpeakerType(context: Context, type: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(PREF_SPEAKER, type).apply()
    }

    fun getSpeakerType(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(PREF_SPEAKER, "") ?: ""
    }


}