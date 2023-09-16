package com.github.flextasker.feature.main.item

import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import com.github.flextasker.R
import com.github.flextasker.core.ui.ext.getValueFromAttribute
import com.github.flextasker.core.ui.recycler.ItemAdapter
import com.github.flextasker.core.ui.recycler.ItemViewHolder
import com.github.flextasker.core.ui.recycler.ListItem
import com.github.flextasker.core.ui.text.Txt
import com.github.flextasker.core.ui.text.of
import com.github.flextasker.databinding.ListItemDrawerMenuItemBinding
import com.github.flextasker.core.model.TaskListInfo
import com.github.flextasker.core.model.TaskListType

data class DrawerMenuItem(
    val text: Txt,
    val icon: Int,
    val type: Type,
    val isSelected: Boolean = false,
) : ListItem {

    val textColorAttribute: Int
        get() = when {
            isSelected -> com.google.android.material.R.attr.colorOnSecondaryContainer
            else -> com.google.android.material.R.attr.colorOnSurface
        }


    override fun layout() = LAYOUT

    override fun areItemsTheSame(that: ListItem): Boolean {
        return that is DrawerMenuItem && this.type.id == that.type.id
    }

    companion object {
        val LAYOUT get() = R.layout.list_item_drawer_menu_item

        fun map(listInfo: TaskListInfo, isSelected: Boolean = false): DrawerMenuItem {
            return when (listInfo.type) {
                TaskListType.STARRED -> DrawerMenuItem(
                    text = Txt.of(R.string.starred),
                    icon = R.drawable.ic_star_filled,
                    type = Type.TaskList(listInfo),
                    isSelected = isSelected,
                )

                TaskListType.DEFAULT -> DrawerMenuItem(
                    text = Txt.of(R.string.default_list_name),
                    icon = R.drawable.ic_checklist,
                    type = Type.TaskList(listInfo),
                    isSelected = isSelected,
                )

                TaskListType.USER -> DrawerMenuItem(
                    text = Txt.of(listInfo.name),
                    icon = R.drawable.ic_checklist,
                    type = Type.TaskList(listInfo),
                    isSelected = isSelected,
                )
            }
        }
    }

    sealed class Type(val id: Long) {

        data class Button(val buttonId: Long) : Type(buttonId)

        data class TaskList(val list: TaskListInfo) : Type(list.id)
    }

    interface Listener {

        fun onClick(position: Int)
    }
}

class DrawerMenuItemViewHolder(
    itemView: View,
    private val listener: DrawerMenuItem.Listener,
) : ItemViewHolder(itemView) {

    private val binding = ListItemDrawerMenuItemBinding.bind(itemView)

    init {
        binding.item.setOnClickListener {
            listener.onClick(adapterPosition)
        }
    }

    override fun bind(item: ListItem) {
        item as DrawerMenuItem

        val textColor = context.getValueFromAttribute(item.textColorAttribute)

        binding.label.setTextColor(textColor)
        binding.label.text = item.text.get(context)
        binding.icon.imageTintList = ColorStateList.valueOf(textColor)
        binding.icon.setImageResource(item.icon)
        binding.item.isSelected = item.isSelected
        binding.label.setTextColor(textColor)
    }
}

class DrawerMenuItemAdapter(
    private val listener: DrawerMenuItem.Listener
) : ItemAdapter {
    override fun viewType() = DrawerMenuItem.LAYOUT

    override fun onCreateViewHolder(parent: ViewGroup): ItemViewHolder {
        return DrawerMenuItemViewHolder(inflateItemView(parent), listener)
    }
}