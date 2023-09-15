package com.github.flextasker.core.api.model

data class NetworkTaskList(
    val id: Long,
    val name: String,
    val type: NetworkTaskListType,
)

enum class NetworkTaskListType {
    DEFAULT, USER
}