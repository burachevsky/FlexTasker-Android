package com.github.flextasker.feature.addtask

import com.github.flextasker.core.ui.di.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [AddTaskModule::class])
interface AddTaskComponent {

    fun inject(dialog: AddTaskDialogFragment)

    interface Provider {
        fun addTaskComponent(module: AddTaskModule): AddTaskComponent
    }
}