package com.github.flextasker

import android.app.Application

class App : Application() {

    val appComponent: AppComponent by lazy(::initializeComponent)

    override fun onCreate() {
        super.onCreate()
    }

    private fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }
}