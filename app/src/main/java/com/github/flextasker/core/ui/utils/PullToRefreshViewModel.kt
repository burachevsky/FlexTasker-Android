package com.github.flextasker.core.ui.utils

import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.flextasker.core.ui.ext.collectOnStarted
import kotlinx.coroutines.flow.MutableStateFlow

interface PullToRefreshViewModel {

    val isRefreshing: MutableStateFlow<Boolean>

    fun startRefreshing() {
        isRefreshing.value = true
        refresh()
    }

    fun refresh()
}

fun Fragment.setupPullToRefresh(
    swipeRefreshLayout: SwipeRefreshLayout,
    viewModel: PullToRefreshViewModel,
) {
    swipeRefreshLayout.setOnRefreshListener {
        viewModel.startRefreshing()
    }

    collectOnStarted(viewModel.isRefreshing, swipeRefreshLayout::setRefreshing)
}