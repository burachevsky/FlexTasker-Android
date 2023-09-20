package com.github.flextasker.feature.taskdetails

import com.github.flextasker.core.ui.constant.NavArg
import dagger.Module
import dagger.Provides

@Module
class TaskDetailsModule(fragment: TaskDetailsFragment) {

    private val taskId = fragment.requireArguments().getLong(NavArg.TASK_ID)

    @Provides
    fun provideTaskId(): Long {
        return taskId
    }
}