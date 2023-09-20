package com.github.flextasker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.flextasker.core.domain.usecase.settings.GetSettings
import com.github.flextasker.core.eventbus.EventBus
import com.github.flextasker.core.model.Settings
import com.github.flextasker.core.ui.container.VM
import com.github.flextasker.core.ui.container.viewModelContainer
import com.github.flextasker.core.ui.event.SwitchTheme
import com.github.flextasker.core.ui.event.ToastMessage
import com.github.flextasker.core.ui.navigation.Navigator
import javax.inject.Inject

class AppViewModel @Inject constructor(
    eventBus: EventBus,
    getSettings: GetSettings,
) : ViewModel(), VM<Navigator> {

    override val container = viewModelContainer()

    private val settings: Settings = getSettings()

    var themeIsInitialized = false

    init {
        eventBus.apply {
            subscribe<ToastMessage>(viewModelScope, container::raiseEffect)
            subscribe<SwitchTheme>(viewModelScope, container::raiseEffect)
        }
    }

    fun dynamicColorsEnabled(): Boolean {
        return settings.dynamicColorsEnabled
    }

    fun getTheme(): Int {
        return settings.theme
    }
}