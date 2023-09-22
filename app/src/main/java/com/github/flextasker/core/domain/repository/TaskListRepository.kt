package com.github.flextasker.core.domain.repository

import com.github.flextasker.core.model.TaskListInfo

interface TaskListRepository {

    suspend fun createTaskList(list: TaskListInfo): TaskListInfo

    suspend fun editTaskList(list: TaskListInfo)

    suspend fun deleteTaskList(id: Long)

    suspend fun getAllTaskLists(): List<TaskListInfo>

    suspend fun getCurrentTaskList(): TaskListInfo

    suspend fun getTaskListInfo(id: Long): TaskListInfo
}