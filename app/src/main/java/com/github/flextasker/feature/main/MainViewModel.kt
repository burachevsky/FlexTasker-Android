package com.github.flextasker.feature.main

import androidx.lifecycle.ViewModel
import com.github.flextasker.core.ui.container.VM
import com.github.flextasker.core.ui.container.viewModelContainer
import com.github.flextasker.core.ui.navigation.Navigator
import javax.inject.Inject

class MainViewModel @Inject constructor(

) : ViewModel(), VM<Navigator> {

    override val container = viewModelContainer()
}