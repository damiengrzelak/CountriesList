package com.example.damian.countries.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.damian.countries.App

private const val SHARED_PREF_NAME = "countries.shared.prefs"
private const val LAST_FETCH = "LAST_FETCH"

var sharedPreferences: SharedPreferences = (App.applicationContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE))

var fetchExpire
    set(value) = sharedPreferences.edit().putLong(LAST_FETCH, value).apply()
    get() = sharedPreferences.getLong(LAST_FETCH, 0)

