package com.github.flextasker.feature.settings

import dagger.Subcomponent

@Subcomponent
interface SettingsComponent {

    fun inject(fragment: SettingsFragment)

    interface Provider {
        fun settingsComponent(): SettingsComponent
    }
}