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
        val id = api.create(task.asNetwork())
        return task.copy(id = id)
    }

    override suspend fun readTask(id: Long): Task {
        return api.read(id).asModel()
    }

    override suspend fun getTasks(listId: Long?, filterStarred: Boolean): List<Task> {
        return api.search(listId, filterStarred).map { it.asModel() }
    }

    override suspend fun editTask(task: Task) {
        api.update(task.asNetwork())
    }

    override suspend fun deleteTask(id: Long) {
        api.delete(id)
    }

    override suspend fun starTask(id: Long, isStarred: Boolean) {
        api.setStarred(id, isStarred)
    }
}