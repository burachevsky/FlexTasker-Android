package com.github.flextasker.core.data

import com.github.flextasker.core.api.AccountApi
import com.github.flextasker.core.api.TaskApi
import com.github.flextasker.core.api.TaskListApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideTaskApi(retrofit: Retrofit): TaskApi = retrofit.create()

    @Provides
    @Singleton
    fun provideTaskListApi(retrofit: Retrofit): TaskListApi = retrofit.create()

    @Provides
    @Singleton
    fun provideAccountApi(retrofit: Retrofit): AccountApi = retrofit.create()
}