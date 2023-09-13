package com.github.flextasker.core.ui.recycler

import android.view.ViewGroup
import com.github.flextasker.core.ui.ext.inflate

interface ItemAdapter {

    fun viewType(): Int

    fun onCreateViewHolder(parent: ViewGroup): ItemViewHolder {
        return ItemViewHolder(inflateItemView(parent))
    }

    fun inflateItemView(parent: ViewGroup) = parent.inflate(viewType())
}