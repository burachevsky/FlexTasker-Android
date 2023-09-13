package com.github.flextasker.core.ui.container

import com.github.flextasker.core.ui.navigation.Navigator

interface VM<N : Navigator> {
    val container: ViewModelContainer<N>
}