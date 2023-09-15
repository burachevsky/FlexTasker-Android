package com.github.flextasker.feature.main.item

import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import com.github.flextasker.R
import com.github.flextasker.core.model.Task
import com.github.flextasker.core.ui.recycler.ItemAdapter
import com.github.flextasker.core.ui.recycler.ItemViewHolder
import com.github.flextasker.core.ui.recycler.ListItem
import com.github.flextasker.databinding.ListItemTaskBinding

data class TaskItem(
    val task: Task,
) : ListItem {
    override fun layout() = VIEW_TYPE

    override fun areItemsTheSame(that: ListItem): Boolean {
        return that is TaskItem && that.task.id == task.id
    }

    companion object {
        val VIEW_TYPE get() = R.layout.list_item_task
    }

    interface Listener {
        fun onClick(position: Int)
        fun onCompleteClick(position: Int)
        fun onStarClick(position: Int)
    }
}

class TaskListItemViewHolder(
    itemView: View,
    private val listener: TaskItem.Listener
) : ItemViewHolder(itemView) {

    private val binding = ListItemTaskBinding.bind(itemView)

    init {
        binding.taskLayout.setOnClickListener {
            listener.onClick(adapterPosition)
        }

        binding.isComplete.setOnClickListener {
            listener.onCompleteClick(adapterPosition)
        }

        binding.isStarred.setOnClickListener {
            listener.onStarClick(adapterPosition)
        }
    }

    override fun bind(item: ListItem) {
        item as TaskItem

        binding.name.apply {
            text = item.task.name
            paintFlags = when {
                item.task.isComplete -> paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                else -> paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
        binding.isComplete.isSelected = item.task.isComplete
        binding.isStarred.isSelected = item.task.isStarred
    }
}

class TaskItemAdapter(
    private val listener: TaskItem.Listener
) : ItemAdapter {

    override fun viewType() = TaskItem.VIEW_TYPE

    override fun onCreateViewHolder(parent: ViewGroup): ItemViewHolder {
        return TaskListItemViewHolder(inflateItemView(parent), listener)
    }
}
