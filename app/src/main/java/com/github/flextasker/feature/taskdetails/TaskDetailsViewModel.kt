package com.github.flextasker.feature.taskdetails

import androidx.lifecycle.ViewModel
import com.github.flextasker.R
import com.github.flextasker.core.domain.usecase.task.DeleteTask
import com.github.flextasker.core.domain.usecase.task.EditTask
import com.github.flextasker.core.domain.usecase.task.GetTask
import com.github.flextasker.core.domain.usecase.list.GetTaskListInfo
import com.github.flextasker.core.eventbus.EventBus
import com.github.flextasker.core.model.Task
import com.github.flextasker.core.model.TaskListInfo
import com.github.flextasker.core.model.TaskListType
import com.github.flextasker.core.ui.container.VM
import com.github.flextasker.core.ui.container.viewModelContainer
import com.github.flextasker.core.ui.event.AlertDialog
import com.github.flextasker.core.ui.event.TaskDeleted
import com.github.flextasker.core.ui.event.TaskUpdated
import com.github.flextasker.core.ui.navigation.Navigator
import com.github.flextasker.core.ui.text.Txt
import com.github.flextasker.core.ui.text.empty
import com.github.flextasker.core.ui.text.of
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
class TaskDetailsViewModel @Inject constructor(
    private val taskId: Long,
    private val eventBus: EventBus,
    private val getTask: GetTask,
    private val getTaskListInfo: GetTaskListInfo,
    private val editTask: EditTask,
    private val deleteTask: DeleteTask,
) : ViewModel(), VM<Navigator> {

    override val container = viewModelContainer()

    private var taskListInfo: TaskListInfo? = null

    private var task: Task? = null

    private val _listName = MutableStateFlow<Txt>(Txt.empty)
    val listName = _listName.asStateFlow()

    private val _isStarred = MutableStateFlow<Boolean?>(null)
    val isStarred = _isStarred.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    private val _isComplete = MutableStateFlow<Boolean?>(null)
    val isComplete = _isComplete.asStateFlow()

    private val updateExecutor = MutableSharedFlow<Updater>()
    private var currentUpdater: Updater? = null

    init {
        container.launch(Dispatchers.Main) {
            val task = getTask(taskId)
            val listInfo = getTaskListInfo(task.listId)

            this@TaskDetailsViewModel.task = task
            taskListInfo = listInfo
            _listName.value = when (listInfo.type) {
                TaskListType.USER -> Txt.of(listInfo.name)
                else -> Txt.of(R.string.default_list_name)
            }
            _isStarred.value = task.isStarred
            _name.value = task.name
            _description.value = task.description
            _isComplete.value = task.isComplete
        }

        container.launch(Dispatchers.Main) {
            updateExecutor
                .onEach {
                    currentUpdater = it
                }
                .debounce(DEBOUNCE_TIMEOUT)
                .collect { updater ->
                    updater.job = launch {
                        updater.action()
                    }
                    updater.job?.join()
                }
        }
    }

    fun back() {
        container.launch(Dispatchers.Main) {
            val millisSinceLastUpdate = System.currentTimeMillis() - (currentUpdater?.launchMillis ?: 0)
            if (millisSinceLastUpdate < DEBOUNCE_TIMEOUT) {
                delay(DEBOUNCE_TIMEOUT - millisSinceLastUpdate)
                currentUpdater?.job?.join()
            }
            container.navigator {
                back()
            }
        }
    }

    fun starClicked() {
        val isStarred = isStarred.value?.not() ?: return
        container.launch(Dispatchers.Main) {
            task = task!!.copy(isStarred = isStarred)
            editTask(task!!)
            eventBus.send(TaskUpdated(task!!))
            _isStarred.value = isStarred
        }
    }

    fun completeClicked() {
        val isComplete = isComplete.value?.not() ?: return
        container.launch(Dispatchers.Main) {
            task = task!!.copy(isComplete = isComplete)
            eventBus.send(TaskUpdated(task!!))
            editTask(task!!)
            _isComplete.value = isComplete
        }
    }

    fun deleteClicked() {
        container.raiseEffect {
            AlertDialog(
                title = Txt.of(R.string.delete_task),
                message = Txt.of(R.string.this_action_cannot_be_undone),
                yes = AlertDialog.Button(
                    text = Txt.of(R.string.button_delete),
                    action = {
                        container.launch(Dispatchers.Main) {
                            deleteTask(taskId)
                            eventBus.send(TaskDeleted(taskId))
                            container.navigator {
                                back()
                            }
                        }
                    }
                ),
                no = AlertDialog.Button(Txt.of(R.string.button_cancel))
            )
        }
    }

    fun nameChanged(text: String) {
        if (text != name.value) {
            _name.value = text
            val updatedTask = task?.copy(name = text) ?: return

            container.launch(Dispatchers.Main) {
                updateExecutor.emit(
                    Updater(
                        action = {
                            task = updatedTask
                            editTask(updatedTask)
                            eventBus.send(TaskUpdated(updatedTask))
                        },
                        launchMillis = System.currentTimeMillis()
                    )
                )
            }
        }
    }

    fun descriptionChanged(text: String) {
        if (text != description.value) {
            _description.value = text
            val updatedTask = task?.copy(description = text) ?: return

            container.launch(Dispatchers.Main) {
                updateExecutor.emit(
                    Updater(
                        action = {
                            task = updatedTask
                            editTask(updatedTask)
                            eventBus.send(TaskUpdated(updatedTask))
                        },
                        launchMillis = System.currentTimeMillis()
                    )
                )
            }
        }
    }

    companion object {
        const val DEBOUNCE_TIMEOUT = 500L
    }
}