package com.github.flextasker.core.api.model

data class NetworkTask(
    val id: Long,
    val name: String,
    val description: String,
    val isComplete: Boolean,
    val isStarred: Boolean,
    val listId: Long,
)
