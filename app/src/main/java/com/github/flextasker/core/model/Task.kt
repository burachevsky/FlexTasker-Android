package com.github.flextasker.core.model

data class Task(
    val id: Long,
    val name: String,
    val description: String,
    val isComplete: Boolean,
    val isStarred: Boolean,
    val listId: Long,
)
