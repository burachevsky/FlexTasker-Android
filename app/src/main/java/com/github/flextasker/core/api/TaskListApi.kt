package com.github.flextasker.core.api

import com.github.flextasker.core.api.model.NetworkTaskList

interface TaskListApi {

    suspend fun create(list: NetworkTaskList): Long

    suspend fun read(id: Long): NetworkTaskList

    suspend fun update(list: NetworkTaskList)

    suspend fun delete(id: Long)

    suspend fun getAll(): List<NetworkTaskList>
}