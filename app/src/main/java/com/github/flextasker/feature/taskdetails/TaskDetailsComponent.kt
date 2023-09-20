package com.github.flextasker.feature.taskdetails

import dagger.Subcomponent

@Subcomponent(modules = [TaskDetailsModule::class])
interface TaskDetailsComponent {

    fun inject(fragment: TaskDetailsFragment)

    interface Provider {
        fun taskDetailsComponent(module: TaskDetailsModule): TaskDetailsComponent
    }
}