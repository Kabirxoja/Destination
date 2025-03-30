package com.example.destination.ui.additions

import android.content.Context

object LanguagePreference {
    private const val PREF_NAME = "language_pref"
    private const val LANGUAGE_KEY = "selected_language"

    fun saveLanguage(context: Context, language: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(LANGUAGE_KEY, language).apply()
    }

    fun getLanguage(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(LANGUAGE_KEY, "uz") ?: "uz"
    }


}