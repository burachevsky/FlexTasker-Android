package com.github.flextasker.core.ui.navigation

import androidx.navigation.NavController

interface NavControllerProvider {

    fun provideNavController(): NavController
}