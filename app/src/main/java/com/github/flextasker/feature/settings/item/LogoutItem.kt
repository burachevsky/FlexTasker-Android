package com.github.flextasker.feature.settings.item

import android.view.View
import android.view.ViewGroup
import com.github.flextasker.R
import com.github.flextasker.core.ui.recycler.ItemAdapter
import com.github.flextasker.core.ui.recycler.ItemViewHolder
import com.github.flextasker.core.ui.recycler.ListItem

object LogoutItem : ListItem {
    override fun layout() = R.layout.list_item_logout

    interface Listener {

        fun onClick()
    }
}

class LogoutItemViewHolder(
    itemView: View,
    listener: LogoutItem.Listener
) : ItemViewHolder(itemView) {

    init {
        itemView.setOnClickListener {
            listener.onClick()
        }
    }
}

class LogoutItemAdapter(
    private val listener: LogoutItem.Listener
) : ItemAdapter {

    override fun viewType() = LogoutItem.layout()

    override fun onCreateViewHolder(parent: ViewGroup): ItemViewHolder {
        return LogoutItemViewHolder(inflateItemView(parent), listener)
    }
}