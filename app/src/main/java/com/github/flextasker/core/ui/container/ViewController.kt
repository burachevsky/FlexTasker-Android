package com.github.flextasker.core.ui.container

import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding

interface ViewController<VM : com.github.flextasker.core.ui.container.VM<*>>
    : LifecycleOwner {

    val binding: ViewBinding
    val viewModel: VM
    val container: ViewContainer
}