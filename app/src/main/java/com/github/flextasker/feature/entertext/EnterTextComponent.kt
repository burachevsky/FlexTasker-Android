package com.github.flextasker.feature.entertext

import dagger.Subcomponent

@Subcomponent(modules = [EnterTextModule::class])
interface EnterTextComponent {

    fun inject(dialog: EnterTextDialogFragment)

    interface Provider {

        fun enterTextComponent(module: EnterTextModule): EnterTextComponent
    }
}