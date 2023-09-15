package com.github.flextasker.core.data.mapper

import com.github.flextasker.core.api.model.NetworkTask
import com.github.flextasker.core.model.Task

fun Task.asNetwork(): NetworkTask {
    return NetworkTask(
        id = id,
        name = name,
        description = description,
        isComplete = isComplete,
        isStarred = isStarred,
        listId = listId,
    )
}

fun NetworkTask.asModel(): Task {
    return Task(
        id = id,
        name = name,
        description = description,
        isComplete = isComplete,
        isStarred = isStarred,
        listId = listId,
    )
}