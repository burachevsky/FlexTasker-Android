package com.github.flextasker.core.data

import com.github.flextasker.core.api.TaskApi
import com.github.flextasker.core.api.TaskListApi
import com.github.flextasker.core.api.fake.FakeTaskApi
import com.github.flextasker.core.api.fake.FakeTaskListApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideTaskApi(): TaskApi = FakeTaskApi()

    @Provides
    @Singleton
    fun provideTaskListApi(): TaskListApi = FakeTaskListApi()
}