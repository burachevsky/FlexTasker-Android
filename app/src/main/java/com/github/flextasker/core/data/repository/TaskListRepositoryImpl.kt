package com.github.flextasker.core.data.repository

import com.github.flextasker.core.api.TaskListApi
import com.github.flextasker.core.data.mapper.asModel
import com.github.flextasker.core.data.mapper.asNetwork
import com.github.flextasker.core.domain.repository.TaskListRepository
import com.github.flextasker.core.model.TaskListInfo
import javax.inject.Inject

class TaskListRepositoryImpl @Inject constructor(
    private val api: TaskListApi,
    private val inMemoryCache: TaskListInMemoryCache,
) : TaskListRepository {

    override suspend fun createTaskList(list: TaskListInfo): TaskListInfo {
        return api.create(list.asNetwork())
            .asModel()
            .also { inMemoryCache.map[it.id] = it }
    }

    override suspend fun editTaskList(list: TaskListInfo) {
        api.update(list.asNetwork())
        inMemoryCache.map[list.id] = list
    }

    override suspend fun deleteTaskList(id: Long) {
        api.delete(id)
        inMemoryCache.map.remove(id)
    }

    override suspend fun getAllTaskLists(): List<TaskListInfo> {
        return api.getAll().map { it.asModel() }
            .onEach { inMemoryCache.map[it.id] = it }
    }

    override suspend fun getCurrentTaskList(): TaskListInfo {
        return inMemoryCache.map.values.first()
    }

    override suspend fun getTaskListInfo(id: Long): TaskListInfo {
        return inMemoryCache.map[id] ?: throw IllegalStateException()
    }
}