package com.github.flextasker.core.ui.constant

sealed class NavDestination {
    data object AddTask : NavDestination()
    data object EnterText : NavDestination()
    data object TaskDetails : NavDestination()
}