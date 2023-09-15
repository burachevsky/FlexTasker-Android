package com.github.flextasker.core.model

data class TaskListInfo(
    val id: Long,
    val name: String,
    val type: TaskListType,
)

enum class TaskListType {
    STARRED, DEFAULT, USER
}