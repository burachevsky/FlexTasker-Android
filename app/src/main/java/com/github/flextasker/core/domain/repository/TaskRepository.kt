package com.github.flextasker.core.domain.repository

import com.github.flextasker.core.model.Task

interface TaskRepository {

    suspend fun addTask(task: Task): Task

    suspend fun readTask(id: Long): Task

    suspend fun getTasks(listId: Long?, filterStarred: Boolean): List<Task>

    suspend fun editTask(task: Task)

    suspend fun deleteTask(id: Long)
}