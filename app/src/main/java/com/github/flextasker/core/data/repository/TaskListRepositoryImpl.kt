package com.github.flextasker.core.data.repository

import com.github.flextasker.core.api.TaskListApi
import com.github.flextasker.core.data.mapper.asModel
import com.github.flextasker.core.data.mapper.asNetwork
import com.github.flextasker.core.domain.repository.TaskListRepository
import com.github.flextasker.core.model.TaskListInfo
import javax.inject.Inject

class TaskListRepositoryImpl @Inject constructor(
    private val api: TaskListApi,
) : TaskListRepository {

    override suspend fun createTaskList(list: TaskListInfo): TaskListInfo {
        val id = api.create(list.asNetwork())
        return list.copy(id = id)
    }

    override suspend fun editTaskList(list: TaskListInfo) {
        api.update(list.asNetwork())
    }

    override suspend fun deleteTaskList(id: Long) {
        api.delete(id)
    }

    override suspend fun getAllTaskLists(): List<TaskListInfo> {
        return api.getAll().map { it.asModel() }
    }

    override suspend fun getCurrentTaskList(): TaskListInfo {
        return api.getAll().first().asModel()
    }
}