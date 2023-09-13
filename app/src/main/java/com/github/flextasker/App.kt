package com.github.flextasker

import android.app.Application
import com.github.flextasker.feature.main.MainComponent
import timber.log.Timber

class App : Application(),
    MainComponent.Provider {

    val appComponent: AppComponent by lazy(::initializeComponent)

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    private fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }

    override fun mainComponent(): MainComponent {
        return appComponent.mainComponent()
    }
}