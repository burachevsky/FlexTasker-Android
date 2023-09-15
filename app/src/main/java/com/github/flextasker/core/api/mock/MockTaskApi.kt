package com.github.flextasker.core.api.mock

import com.github.flextasker.core.api.TaskApi
import com.github.flextasker.core.api.model.NetworkTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class MockTaskApi: TaskApi {

    private val inMemoryCache = MutableStateFlow<List<NetworkTask>>(initialState())

    override suspend fun create(task: NetworkTask): Long {
        val newTask = task.copy(id = ++taskId)

        inMemoryCache.update {
            it + newTask
        }

        return newTask.id
    }

    override suspend fun read(id: Long): NetworkTask {
        return inMemoryCache.value.find { it.id == id }
            ?: throw IllegalStateException()
    }

    override suspend fun search(listId: Long?, filterStarred: Boolean): List<NetworkTask> {
        return inMemoryCache.value
            .let { list ->
                when {
                    listId != null -> list.filter { it.listId == listId }
                    else -> list
                }
            }
            .let { list ->
                when {
                    filterStarred -> list.filter { it.isStarred }
                    else -> list
                }
            }
    }

    override suspend fun update(task: NetworkTask) {
        inMemoryCache.update { list ->
            list.map {
                if (it.id == task.id) task
                else it
            }
        }
    }

    override suspend fun delete(id: Long) {
        inMemoryCache.update { list ->
            list.filter { it.id != id }
        }
    }

    override suspend fun setStarred(id: Long, isStarred: Boolean) {
        inMemoryCache.update { list ->
            list.map {
                if (it.id == id) it.copy(isStarred = isStarred)
                else it
            }
        }
    }

    private fun initialState(): List<NetworkTask> {
        return listOf(
            NetworkTask(
                id = ++taskId,
                name = "Task 1",
                description = "Task 1 Description",
                isComplete = false,
                isStarred = false,
                listId = 0,
            ),
            NetworkTask(
                id = ++taskId,
                name = "Task 2",
                description = "Task 2 Description",
                isComplete = true,
                isStarred = false,
                listId = 0,
            ),
            NetworkTask(
                id = ++taskId,
                name = "Task 3",
                description = "Task 3 Description",
                isComplete = false,
                isStarred = true,
                listId = 0,
            ),
            NetworkTask(
                id = ++taskId,
                name = "Task 4",
                description = "Task 4 Description",
                isComplete = true,
                isStarred = true,
                listId = 0,
            ),
        )
    }

    companion object {
        private var taskId = 0L
    }
}