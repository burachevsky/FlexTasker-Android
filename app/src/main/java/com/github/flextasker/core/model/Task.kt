package com.github.flextasker.core.model

data class Task(
    val id: Long = 0,
    val name: String,
    val description: String,
    val isComplete: Boolean = false,
    val isStarred: Boolean,
    val listId: Long,
)
