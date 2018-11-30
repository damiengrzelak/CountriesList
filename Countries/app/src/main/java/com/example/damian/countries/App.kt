package com.example.damian.countries

import android.app.Application
import android.content.Context

class App : Application() {
    init {
        instance = this
    }

    companion object {
        private lateinit var instance: App

        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }
}