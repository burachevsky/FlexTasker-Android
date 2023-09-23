package com.github.flextasker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.flextasker.core.domain.AccountInfo
import com.github.flextasker.core.domain.usecase.settings.GetSettings
import com.github.flextasker.core.eventbus.EventBus
import com.github.flextasker.core.model.Settings
import com.github.flextasker.core.ui.container.VM
import com.github.flextasker.core.ui.container.viewModelContainer
import com.github.flextasker.core.ui.event.SwitchTheme
import com.github.flextasker.core.ui.event.ToastMessage
import com.github.flextasker.core.ui.event.UserChanged
import com.github.flextasker.core.ui.navigation.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class AppViewModel @Inject constructor(
    eventBus: EventBus,
    getSettings: GetSettings,
    private val accountInfo: AccountInfo,
) : ViewModel(), VM<Navigator> {

    override val container = viewModelContainer()

    private val settings: Settings = getSettings()

    var themeIsInitialized = false

    private val _appBarsInitialized = MutableStateFlow(false)
    val appBarsInitialized = _appBarsInitialized.asStateFlow()

    private val _userSignedIn = MutableStateFlow<Boolean?>(null)
    val userSignedIn = _userSignedIn.asStateFlow()

    init {
        eventBus.apply {
            subscribe<ToastMessage>(viewModelScope, container::raiseEffect)
            subscribe<SwitchTheme>(viewModelScope, container::raiseEffect)
            subscribe<UserChanged>(viewModelScope) { update() }
        }

        update()
    }

    fun setAppBarsInitialized(initialized: Boolean) {
        _appBarsInitialized.value = initialized
    }

    fun dynamicColorsEnabled(): Boolean {
        return settings.dynamicColorsEnabled
    }

    fun getTheme(): Int {
        return settings.theme
    }

    private fun update() {
        container.launch(Dispatchers.IO) {
            _userSignedIn.emit(accountInfo.isUserSignedIn())
        }
    }
}