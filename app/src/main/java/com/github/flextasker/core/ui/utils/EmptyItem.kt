package com.github.flextasker.core.ui.utils

import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.core.view.updateLayoutParams
import com.github.flextasker.R
import com.github.flextasker.core.ui.recycler.ItemAdapter
import com.github.flextasker.core.ui.recycler.ItemViewHolder
import com.github.flextasker.core.ui.recycler.ListItem
import com.github.flextasker.databinding.ListItemEmptyBinding

data class EmptyItem(
    @DimenRes val height: Int
) : ListItem {
    override fun layout() = R.layout.list_item_empty

    override fun areItemsTheSame(that: ListItem): Boolean {
        return this == that
    }

    companion object {
        val VIEW_TYPE get() = R.layout.list_item_empty
    }
}

class EmptyItemViewHolder(itemView: View): ItemViewHolder(itemView) {

    private val binding = ListItemEmptyBinding.bind(itemView)

    override fun bind(item: ListItem) {
        item as EmptyItem

        binding.emptyLayout.updateLayoutParams {
            height = context.resources.getDimensionPixelSize(item.height)
        }
    }
}

class EmptyItemAdapter : ItemAdapter {

    override fun viewType() = EmptyItem.VIEW_TYPE

    override fun onCreateViewHolder(parent: ViewGroup): ItemViewHolder {
        return EmptyItemViewHolder(inflateItemView(parent))
    }
}