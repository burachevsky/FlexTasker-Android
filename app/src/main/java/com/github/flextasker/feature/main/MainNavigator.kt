package com.github.flextasker.feature.main

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.github.flextasker.core.ui.constant.NavArg
import com.github.flextasker.core.ui.constant.NavDestination
import com.github.flextasker.core.ui.container.NavDestinationMapper
import com.github.flextasker.core.ui.navigation.Navigator

class MainNavigator(
    navController: NavController,
    action: NavDestinationMapper
) : Navigator(navController, action) {

    fun navigateAddTask(listId: Long) =
        navigate(
            NavDestination.AddTask,
            bundleOf(
                NavArg.LIST_ID to listId
            )
        )
}