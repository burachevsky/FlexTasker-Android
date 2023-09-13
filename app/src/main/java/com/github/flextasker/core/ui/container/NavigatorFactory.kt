package com.github.flextasker.core.ui.container

import androidx.navigation.NavController
import com.github.flextasker.core.ui.navigation.Navigator

fun interface NavigatorFactory {
    fun createNavigator(
        navController: NavController,
        actionProvider: NavDestinationMapper,
    ): Navigator
}