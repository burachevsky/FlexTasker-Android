package com.github.flextasker

import android.content.Context
import com.github.flextasker.core.data.ApiModule
import com.github.flextasker.core.data.NetworkModule
import com.github.flextasker.core.data.PreferencesModule
import com.github.flextasker.core.data.RepositoryModule
import com.github.flextasker.feature.addtask.AddTaskComponent
import com.github.flextasker.feature.addtask.AddTaskModule
import com.github.flextasker.feature.auth.AuthComponent
import com.github.flextasker.feature.entertext.EnterTextComponent
import com.github.flextasker.feature.entertext.EnterTextModule
import com.github.flextasker.feature.main.MainComponent
import com.github.flextasker.feature.settings.SettingsComponent
import com.github.flextasker.feature.taskdetails.TaskDetailsComponent
import com.github.flextasker.feature.taskdetails.TaskDetailsModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        PreferencesModule::class,
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: AppActivity)

    fun mainComponent(): MainComponent

    fun addTaskComponent(module: AddTaskModule): AddTaskComponent

    fun enterTextComponent(module: EnterTextModule): EnterTextComponent

    fun taskDetailsComponent(module: TaskDetailsModule): TaskDetailsComponent

    fun settingsComponent(): SettingsComponent

    fun authComponent(): AuthComponent
}