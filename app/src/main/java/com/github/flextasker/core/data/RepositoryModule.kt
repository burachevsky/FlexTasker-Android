package com.github.flextasker.core.data

import com.github.flextasker.core.data.repository.TaskListRepositoryImpl
import com.github.flextasker.core.data.repository.TaskRepositoryImpl
import com.github.flextasker.core.data.repository.UserRepositoryImpl
import com.github.flextasker.core.domain.repository.TaskListRepository
import com.github.flextasker.core.domain.repository.TaskRepository
import com.github.flextasker.core.domain.repository.UserRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindTaskRepository(impl: TaskRepositoryImpl): TaskRepository

    @Binds
    abstract fun bindTaskListRepository(impl: TaskListRepositoryImpl): TaskListRepository

    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}