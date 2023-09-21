package com.github.flextasker.core.ui.utils

import android.view.View
import android.view.ViewGroup
import com.github.flextasker.R
import com.github.flextasker.core.ui.recycler.ItemAdapter
import com.github.flextasker.core.ui.recycler.ItemViewHolder
import com.github.flextasker.core.ui.recycler.ListItem
import com.github.flextasker.core.ui.text.Txt
import com.github.flextasker.databinding.ListItemTextBinding

data class TextItem(
    val text: Txt,
) : ListItem {

    override fun layout() = VIEW_TYPE

    companion object {
        val VIEW_TYPE get() = R.layout.list_item_text
    }
}

class TextViewHolder(itemView: View) : ItemViewHolder(itemView) {

    private val binding = ListItemTextBinding.bind(itemView)

    override fun bind(item: ListItem) {
        item as TextItem
        binding.textView.text = item.text.get(context)
    }
}

class TextItemAdapter : ItemAdapter {

    override fun viewType() = TextItem.VIEW_TYPE

    override fun onCreateViewHolder(parent: ViewGroup): ItemViewHolder {
        return TextViewHolder(inflateItemView(parent))
    }
}