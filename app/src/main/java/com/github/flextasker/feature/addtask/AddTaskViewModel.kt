package com.github.flextasker.feature.addtask

import androidx.lifecycle.ViewModel
import com.github.flextasker.core.domain.usecase.AddTask
import com.github.flextasker.core.eventbus.EventBus
import com.github.flextasker.core.model.Task
import com.github.flextasker.core.ui.container.VM
import com.github.flextasker.core.ui.container.viewModelContainer
import com.github.flextasker.core.ui.event.TaskAdded
import com.github.flextasker.core.ui.navigation.Navigator
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class AddTaskViewModel @Inject constructor(
    private val addTask: AddTask,
    private val eventBus: EventBus,
    private val listId: Long,
) : ViewModel(), VM<Navigator> {

    override val container = viewModelContainer()

    fun submit(name: String, description: String, isStarred: Boolean) {
        container.launch(Dispatchers.Main) {
            val task = addTask(
                Task(
                    name = name,
                    description = description,
                    isStarred = isStarred,
                    listId = listId
                )
            )

            eventBus.send(TaskAdded(task))

            container.navigator { back() }
        }
    }
}