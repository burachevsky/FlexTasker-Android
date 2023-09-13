package com.github.flextasker.feature.main

import com.github.flextasker.core.ui.di.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface MainComponent {

    fun inject(fragment: MainFragment)

    interface Provider {
        fun mainComponent(): MainComponent
    }
}