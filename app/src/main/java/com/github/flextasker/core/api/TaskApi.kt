package com.github.flextasker.core.api

import com.github.flextasker.core.api.model.NetworkTask

interface TaskApi {

    suspend fun create(task: NetworkTask): Long

    suspend fun read(id: Long): NetworkTask

    suspend fun search(listId: Long?, filterStarred: Boolean): List<NetworkTask>

    suspend fun update(task: NetworkTask)

    suspend fun delete(id: Long)

    suspend fun setStarred(id: Long, isStarred: Boolean)
}