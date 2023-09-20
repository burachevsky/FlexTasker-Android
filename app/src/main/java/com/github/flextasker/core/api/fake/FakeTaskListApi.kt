package com.github.flextasker.core.api.fake

import com.github.flextasker.core.api.TaskListApi
import com.github.flextasker.core.api.model.NetworkTaskList
import com.github.flextasker.core.api.model.NetworkTaskListType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeTaskListApi : TaskListApi {

    private val inMemoryCache = MutableStateFlow(initialState())

    override suspend fun create(list: NetworkTaskList): Long {
        val newList = list.copy(id = ++listId)

        inMemoryCache.update {
            it + newList
        }

        return newList.id
    }

    override suspend fun read(id: Long): NetworkTaskList {
        return inMemoryCache.value.find { it.id == id }!!
    }

    override suspend fun update(list: NetworkTaskList) {
        inMemoryCache.update { taskLists ->
            taskLists.map {
                if (it.id == list.id) list
                else it
            }
        }
    }

    override suspend fun delete(id: Long) {
        inMemoryCache.update { list ->
            list.filter { it.id != id }
        }
    }

    override suspend fun getAll(): List<NetworkTaskList> {
        return inMemoryCache.value
    }

    private fun initialState(): List<NetworkTaskList> {
        return listOf(
            NetworkTaskList(
                id = 0,
                name = "",
                type = NetworkTaskListType.DEFAULT
            )
        )
    }

    companion object {
        private var listId = 0L
    }
}