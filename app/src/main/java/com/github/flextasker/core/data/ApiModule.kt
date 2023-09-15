package com.github.flextasker.core.data

import com.github.flextasker.core.api.TaskApi
import com.github.flextasker.core.api.TaskListApi
import com.github.flextasker.core.api.mock.MockTaskApi
import com.github.flextasker.core.api.mock.MockTaskListApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideTaskApi(): TaskApi = MockTaskApi()

    @Provides
    @Singleton
    fun provideTaskListApi(): TaskListApi = MockTaskListApi()
}