package com.github.flextasker

import android.content.Context
import com.github.flextasker.core.data.ApiModule
import com.github.flextasker.core.data.NetworkModule
import com.github.flextasker.core.data.RepositoryModule
import com.github.flextasker.feature.main.MainComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        NetworkModule::class,
        RepositoryModule::class,
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: AppActivity)

    fun mainComponent(): MainComponent
}