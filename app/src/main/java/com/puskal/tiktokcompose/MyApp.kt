package com.puskal.tiktokcompose

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Puskal Khadka on 3/14/2023.
 */
@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}