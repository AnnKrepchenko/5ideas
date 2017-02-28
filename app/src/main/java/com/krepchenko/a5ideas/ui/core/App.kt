package com.krepchenko.a5ideas.ui.core

import android.app.Application
import com.facebook.stetho.Stetho

/**
 * Created by Ann
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}