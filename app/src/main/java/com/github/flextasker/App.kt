package com.github.flextasker

import android.app.Application
import com.github.flextasker.feature.addtask.AddTaskComponent
import com.github.flextasker.feature.addtask.AddTaskModule
import com.github.flextasker.feature.entertext.EnterTextComponent
import com.github.flextasker.feature.entertext.EnterTextModule
import com.github.flextasker.feature.main.MainComponent
import com.github.flextasker.feature.settings.SettingsComponent
import com.github.flextasker.feature.taskdetails.TaskDetailsComponent
import com.github.flextasker.feature.taskdetails.TaskDetailsModule
import timber.log.Timber

class App : Application(),
    MainComponent.Provider,
    AddTaskComponent.Provider,
    EnterTextComponent.Provider,
    TaskDetailsComponent.Provider,
    SettingsComponent.Provider {

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

    override fun addTaskComponent(module: AddTaskModule): AddTaskComponent {
        return appComponent.addTaskComponent(module)
    }

    override fun enterTextComponent(module: EnterTextModule): EnterTextComponent {
        return appComponent.enterTextComponent(module)
    }

    override fun taskDetailsComponent(module: TaskDetailsModule): TaskDetailsComponent {
        return appComponent.taskDetailsComponent(module)
    }

    override fun settingsComponent(): SettingsComponent {
        return appComponent.settingsComponent()
    }
}