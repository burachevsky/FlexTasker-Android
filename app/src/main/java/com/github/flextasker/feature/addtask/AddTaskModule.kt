package com.github.flextasker.feature.addtask

import com.github.flextasker.core.ui.constant.NavArg
import dagger.Module
import dagger.Provides

@Module
class AddTaskModule(fragment: AddTaskDialogFragment) {

    private val listId: Long = fragment.requireArguments().getLong(NavArg.LIST_ID)

    @Provides
    fun provideListId(): Long {
        return listId
    }
}