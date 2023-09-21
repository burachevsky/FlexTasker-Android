package com.github.flextasker.feature.main

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.github.flextasker.core.ui.constant.NavArg
import com.github.flextasker.core.ui.constant.NavDestination
import com.github.flextasker.core.ui.container.NavDestinationMapper
import com.github.flextasker.core.ui.navigation.Navigator
import com.github.flextasker.core.ui.text.ParcelableTxt

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

    fun navigateEnterText(
        title: ParcelableTxt,
        initText: ParcelableTxt? = null,
        actionId: Int,
    ) = navigate(
        NavDestination.EnterText,
        bundleOf(
            NavArg.TITLE to title,
            NavArg.INIT_TEXT to initText,
            NavArg.ACTION_ID to actionId,
        )
    )

    fun navigateTaskDetails(
        taskId: Long,
    ) = navigate(
        NavDestination.TaskDetails,
        bundleOf(
            NavArg.TASK_ID to taskId
        )
    )

    fun navigateSettings() = navigate(NavDestination.Settings)
}