package com.github.flextasker.feature.main

import androidx.lifecycle.ViewModel
import com.github.flextasker.R
import com.github.flextasker.core.model.Task
import com.github.flextasker.core.ui.container.VM
import com.github.flextasker.core.ui.container.viewModelContainer
import com.github.flextasker.core.ui.navigation.Navigator
import com.github.flextasker.core.ui.recycler.ListItem
import com.github.flextasker.core.ui.text.Txt
import com.github.flextasker.core.ui.text.of
import com.github.flextasker.feature.main.item.TaskItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class MainViewModel @Inject constructor(

) : ViewModel(), VM<Navigator> {

    override val container = viewModelContainer()

    private val _currentListName = MutableStateFlow<Txt>(Txt.of(R.string.default_list_name))
    val currentListName = _currentListName.asStateFlow()

    private val _items = MutableStateFlow<List<ListItem>>(testItems())
    val items = _items.asStateFlow()

    fun onTaskClick(position: Int) {

    }

    fun onTaskCompleteClicked(position: Int) {
        updateTaskItem(position) {
            copy(isComplete = !isComplete)
        }
    }

    fun onTaskStarClicked(position: Int) {
        updateTaskItem(position) {
            copy(isStarred = !isStarred)
        }
    }

    private fun updateTaskItem(position: Int, update: Task.() -> Task) {
        _items.update { list ->
            list.toMutableList().apply {
                val item = this[position] as TaskItem

                this[position] = item.run {
                    copy(
                        task = task.update()
                    )
                }
            }
        }
    }

    private fun testItems(): List<ListItem> {
        return listOf(
            TaskItem(
                Task(
                    id = 1,
                    name = "Task 1",
                    description = "",
                    isComplete = false,
                    isStarred = false,
                )
            ),
            TaskItem(
                Task(
                    id = 2,
                    name = "Task 2",
                    description = "",
                    isComplete = false,
                    isStarred = false,
                )
            ),
            TaskItem(
                Task(
                    id = 3,
                    name = "Task 3",
                    description = "",
                    isComplete = true,
                    isStarred = false,
                )
            ),
            TaskItem(
                Task(
                    id = 4,
                    name = "Task 2",
                    description = "",
                    isComplete = false,
                    isStarred = true,
                )
            ),
            TaskItem(
                Task(
                    id = 5,
                    name = "Task 4",
                    description = "",
                    isComplete = true,
                    isStarred = true,
                )
            ),
        )
    }
}