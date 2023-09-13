package com.github.flextasker.core.ui.container

import com.github.flextasker.core.ui.constant.NavDestination

fun interface NavDestinationMapper {

    fun map(destination: NavDestination): Int

    interface Provider {
        fun provideNavDestinationMapper(): NavDestinationMapper
    }
}