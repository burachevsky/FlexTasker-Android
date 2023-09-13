package com.github.flextasker

import com.github.flextasker.core.ui.container.SystemBarsSizeProvider

object AppSystemBarsSizeProvider : SystemBarsSizeProvider {
    var isInitialized = false

    override var statusBarHeight = 0
    override var navigationBarHeight = 0
}