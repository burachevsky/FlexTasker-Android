package com.github.flextasker.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.flextasker.R
import com.github.flextasker.core.domain.usecase.CreateTaskList
import com.github.flextasker.core.domain.usecase.EditTask
import com.github.flextasker.core.domain.usecase.GetCurrentListInfo
import com.github.flextasker.core.domain.usecase.GetTaskLists
import com.github.flextasker.core.domain.usecase.GetTasks
import com.github.flextasker.core.eventbus.EventBus
import com.github.flextasker.core.model.Task
import com.github.flextasker.core.model.TaskListInfo
import com.github.flextasker.core.model.TaskListType
import com.github.flextasker.core.ui.container.VM
import com.github.flextasker.core.ui.container.viewModelContainer
import com.github.flextasker.core.ui.event.EnterTextAction
import com.github.flextasker.core.ui.event.EnterTextResult
import com.github.flextasker.core.ui.event.TaskAdded
import com.github.flextasker.core.ui.event.TaskDeleted
import com.github.flextasker.core.ui.event.TaskEvent
import com.github.flextasker.core.ui.event.TaskUpdated
import com.github.flextasker.core.ui.recycler.ListItem
import com.github.flextasker.core.ui.text.Txt
import com.github.flextasker.core.ui.text.of
import com.github.flextasker.core.ui.utils.DividerItem
import com.github.flextasker.core.ui.utils.EmptyItem
import com.github.flextasker.core.ui.utils.PullToRefreshViewModel
import com.github.flextasker.feature.main.item.DrawerHeaderItem
import com.github.flextasker.feature.main.item.DrawerMenuItem
import com.github.flextasker.feature.main.item.TaskItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getTasks: GetTasks,
    private val editTask: EditTask,
    private val getTaskLists: GetTaskLists,
    private val getCurrentListInfo: GetCurrentListInfo,
    private val createTaskList: CreateTaskList,
    eventBus: EventBus,
) : ViewModel(), VM<MainNavigator>, PullToRefreshViewModel {

    override val container = viewModelContainer()

    private val _items = MutableStateFlow<List<ListItem>>(emptyList())
    val items = _items.asStateFlow()

    private val _drawerItems = MutableStateFlow<List<ListItem>>(emptyList())
    val drawerItems = _drawerItems.asStateFlow()

    private val selectedList = MutableStateFlow<DrawerMenuItem?>(null)

    val selectedListName: Flow<Txt?> = selectedList.map { it?.text }

    override val isRefreshing = MutableStateFlow(false)

    init {
        container.launch(Dispatchers.Main) {
            init()
            refresh()
        }

        eventBus.apply {
            subscribe(viewModelScope, ::handleTaskEvent)
            subscribe(viewModelScope, ::handleEnterTextResult)
        }
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

    fun newTaskClicked() {
        container.navigator {
            selectedList.value?.type?.id?.let { listId ->
                navigateAddTask(listId = listId)
            }
        }
    }

    fun drawerMenuItemClicked(position: Int) {
        val menuItem = drawerItems.value[position] as DrawerMenuItem
        when (menuItem.type) {
            is DrawerMenuItem.Type.Button -> when (menuItem.type.id) {
                BUTTON_CREATE_LIST -> {
                    closeDrawer()
                    container.navigator {
                        navigateEnterText(
                            title = Txt.of(R.string.create_list),
                            actionId = EnterTextAction.NEW_LIST,
                        )
                    }
                }
            }

            is DrawerMenuItem.Type.TaskList -> {
                _drawerItems.update { list ->
                    list.mapIndexed { i, it ->
                        if (it is DrawerMenuItem && it.type is DrawerMenuItem.Type.TaskList) {
                            if (i == position) {
                                selectedList.value = it
                                it.copy(isSelected = true)
                            } else if (it.isSelected) {
                                it.copy(isSelected = false)
                            } else it
                        } else it
                    }
                }

                refresh()
                closeDrawer()
            }
        }
    }

    override fun refresh() {
        container.launch(Dispatchers.Main) {
            try {
                loadTasks()
            } finally {
                isRefreshing.value = false
            }
        }
    }

    private suspend fun loadTasks() {
        val taskListMenuItem = selectedList.value?.type as? DrawerMenuItem.Type.TaskList
        val isStarred = taskListMenuItem?.list?.type == TaskListType.STARRED

        _items.value = getTasks(
            listId = if (isStarred) null else taskListMenuItem?.id,
            filterStarred = isStarred,
        ).map(::TaskItem)
    }

    private suspend fun init() {
        val selected = DrawerMenuItem.map(getCurrentListInfo(), isSelected = true)
        selectedList.value = selected
        _drawerItems.value = composeDrawerMenuList(getTaskLists(), selected)
    }

    private fun handleTaskEvent(event: TaskEvent) {
        when (event) {
            is TaskAdded -> {
                _items.update {
                    it + TaskItem(event.task)
                }
            }

            is TaskUpdated -> {
                val task = event.task
                _items.update { list ->
                    list.map {
                        if (it is TaskItem && it.task.id == task.id)
                            it.copy(task = task)
                        else it
                    }
                }
            }

            is TaskDeleted -> {
                _items.update { list ->
                    list.filter {
                        it !is TaskItem || it.task.id != event.id
                    }
                }
            }
        }
    }

    private fun handleEnterTextResult(event: EnterTextResult) {
        when (event.actionId) {
            EnterTextAction.NEW_LIST -> {
                container.launch(Dispatchers.Main) {
                    val newList = createTaskList(
                        TaskListInfo(name = event.enteredText, type = TaskListType.USER)
                    )

                    val newListPosition = drawerItems.value.indexOfLast {
                        it is DrawerMenuItem && it.type is DrawerMenuItem.Type.TaskList
                    } + 1

                    _drawerItems.value = _drawerItems.value.toMutableList().apply {
                        add(newListPosition, DrawerMenuItem.map(newList))
                    }

                    drawerMenuItemClicked(newListPosition)
                }
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

    private fun composeDrawerMenuList(
        userLists: List<TaskListInfo>,
        selectedItem: DrawerMenuItem,
    ): List<ListItem> {
        val selectedList = (selectedItem.type as DrawerMenuItem.Type.TaskList).list
        val selectedIsDefault = selectedList.type == TaskListType.DEFAULT
        val selectedIsStarred = selectedList.type == TaskListType.STARRED

        return ArrayList<ListItem>().apply {
            add(DrawerHeaderItem)
            add(DividerItem)
            add(EmptyItem(R.dimen.drawer_item_spacing))
            add(
                DrawerMenuItem.map(
                    TaskListInfo(type = TaskListType.STARRED),
                    isSelected = selectedIsStarred
                ),
            )
            addAll(
                userLists.map {
                    val isDefault = it.type == TaskListType.DEFAULT && selectedIsDefault
                    val isUser = it.type == TaskListType.USER && selectedItem.type.id == it.id

                    DrawerMenuItem.map(listInfo = it, isSelected = isDefault || isUser)
                }
            )
            add(EmptyItem(R.dimen.drawer_item_spacing))
            add(DividerItem)
            add(
                DrawerMenuItem(
                    icon = R.drawable.ic_add,
                    text = Txt.of(R.string.create_list),
                    type = DrawerMenuItem.Type.Button(BUTTON_CREATE_LIST)
                )
            )
        }
    }

    private fun closeDrawer() {
        container.launch(Dispatchers.Main) {
            delay(150)
            container.raiseEffect(CloseDrawer)
        }
    }

    companion object {
        const val BUTTON_CREATE_LIST = 0L
    }
}
