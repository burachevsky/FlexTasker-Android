package com.github.flextasker.feature.auth

import androidx.navigation.NavController
import com.github.flextasker.core.ui.constant.NavDestination
import com.github.flextasker.core.ui.container.NavDestinationMapper
import com.github.flextasker.core.ui.navigation.Navigator

class AuthNavigator(
    navController: NavController,
    action: NavDestinationMapper,
) : Navigator(navController, action) {

    fun navigateSignUp() = navigate(NavDestination.SignUp)
}