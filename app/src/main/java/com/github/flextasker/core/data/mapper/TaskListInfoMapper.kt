package com.github.flextasker.core.data.mapper

import com.github.flextasker.core.api.model.NetworkTaskList
import com.github.flextasker.core.api.model.NetworkTaskListType
import com.github.flextasker.core.model.TaskListInfo
import com.github.flextasker.core.model.TaskListType

fun TaskListInfo.asNetwork(): NetworkTaskList {
    return NetworkTaskList(
        id = id,
        name = name,
        type = type.asNetwork(),
    )
}

fun TaskListType.asNetwork(): NetworkTaskListType {
    return when (this) {
        TaskListType.USER -> NetworkTaskListType.DEFAULT
        else -> NetworkTaskListType.DEFAULT
    }
}

fun NetworkTaskList.asModel(): TaskListInfo {
    return TaskListInfo(
        id = id,
        name = name,
        type = type.asModel(),
    )
}

fun NetworkTaskListType.asModel(): TaskListType {
    return when (this) {
        NetworkTaskListType.USER -> TaskListType.DEFAULT
        else -> TaskListType.DEFAULT
    }
}
