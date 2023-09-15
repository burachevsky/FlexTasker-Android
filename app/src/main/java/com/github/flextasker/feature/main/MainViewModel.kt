package com.github.flextasker.feature.main

import androidx.lifecycle.ViewModel
import com.github.flextasker.R
import com.github.flextasker.core.domain.usecase.EditTask
import com.github.flextasker.core.domain.usecase.GetTasks
import com.github.flextasker.core.model.Task
import com.github.flextasker.core.ui.container.VM
import com.github.flextasker.core.ui.container.viewModelContainer
import com.github.flextasker.core.ui.navigation.Navigator
import com.github.flextasker.core.ui.recycler.ListItem
import com.github.flextasker.core.ui.text.Txt
import com.github.flextasker.core.ui.text.of
import com.github.flextasker.core.ui.utils.PullToRefreshViewModel
import com.github.flextasker.feature.main.item.TaskItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getTasks: GetTasks,
    private val editTask: EditTask,
) : ViewModel(), VM<Navigator>, PullToRefreshViewModel {

    override val container = viewModelContainer()

    private val _currentListName = MutableStateFlow<Txt>(Txt.of(R.string.default_list_name))
    val currentListName = _currentListName.asStateFlow()

    private val _items = MutableStateFlow<List<ListItem>>(emptyList())
    val items = _items.asStateFlow()

    override val isRefreshing = MutableStateFlow(false)

    init {
        refresh()
    }

    fun onTaskClick(position: Int) {

    }

    fun onTaskCompleteClicked(position: Int) {
        container.launch(Dispatchers.Default) {
            updateTaskItem(position) {
                copy(isComplete = !isComplete)
            }
        }
    }

    fun onTaskStarClicked(position: Int) {
        container.launch(Dispatchers.Default) {
            updateTaskItem(position) {
                copy(isStarred = !isStarred)
            }
        }
    }

    override fun refresh() {
        container.launch(Dispatchers.Main) {
            try {
                _items.value = getTasks(listId = null, filterStarred = false)
                    .map(::TaskItem)
            } finally {
                isRefreshing.value = false
            }
        }
    }

    private suspend fun updateTaskItem(position: Int, update: suspend Task.() -> Task) {
        _items.update { list ->
            list.toMutableList().apply {
                val item = this[position] as TaskItem

                this[position] = item.run {
                    val updatedTask = task.update()
                    editTask(updatedTask)
                    copy(task = updatedTask)
                }
            }
        }
    }
}