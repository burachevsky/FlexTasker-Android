package com.github.flextasker.core.data.repository

import com.github.flextasker.core.api.TaskApi
import com.github.flextasker.core.data.mapper.asModel
import com.github.flextasker.core.data.mapper.asNetwork
import com.github.flextasker.core.domain.repository.TaskRepository
import com.github.flextasker.core.model.Task
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val api: TaskApi,
) : TaskRepository {

    override suspend fun addTask(task: Task): Task {
        return api.create(task.asNetwork()).asModel()
    }

    override suspend fun readTask(id: Long): Task {
        return api.read(id).asModel()
    }

    override suspend fun getTasks(listId: Long?, filterStarred: Boolean): List<Task> {
        val list = when {
            listId != null -> api.getTaskList(listId)
            filterStarred -> api.getStarredTasks()
            else -> api.getTasks()
        }

        return list.map { it.asModel() }
    }

    override suspend fun editTask(task: Task) {
        api.update(task.asNetwork())
    }

    override suspend fun deleteTask(id: Long) {
        api.delete(id)
    }
}