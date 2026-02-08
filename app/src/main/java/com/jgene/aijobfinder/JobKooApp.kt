package com.jgene.aijobfinder

import android.app.Application
import com.jgene.aijobfinder.di.AppContainer

class JobKooApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer()
    }
}
