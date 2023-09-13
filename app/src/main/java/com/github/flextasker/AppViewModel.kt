package com.github.flextasker

import androidx.lifecycle.ViewModel
import com.github.flextasker.core.ui.container.VM
import com.github.flextasker.core.ui.container.viewModelContainer
import com.github.flextasker.core.ui.navigation.Navigator
import javax.inject.Inject

class AppViewModel @Inject constructor() : ViewModel(), VM<Navigator> {
    override val container = viewModelContainer()
}