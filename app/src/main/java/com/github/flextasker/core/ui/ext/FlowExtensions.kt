package com.github.flextasker.core.ui.ext

import com.github.flextasker.core.ui.recycler.ListItem
import kotlinx.coroutines.flow.StateFlow

inline fun <reified R> StateFlow<List<ListItem>>.get(position: Int): R {
    return value[position] as R
}
