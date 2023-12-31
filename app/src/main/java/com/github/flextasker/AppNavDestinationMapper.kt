package com.github.flextasker

import com.github.flextasker.core.ui.constant.NavDestination
import com.github.flextasker.core.ui.container.NavDestinationMapper

object AppNavDestinationMapper : NavDestinationMapper {
    override fun map(destination: NavDestination): Int {
        return when (destination) {
            NavDestination.AddTask -> R.id.navigateAddTask
            NavDestination.EnterText -> R.id.navigateEnterText
            NavDestination.TaskDetails -> R.id.navigateTaskDetails
            NavDestination.Settings -> R.id.navigateSettings
            NavDestination.SignUp -> R.id.navigateSignUp
        }
    }
}