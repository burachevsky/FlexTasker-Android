package com.github.flextasker.core.data.mapper

import com.github.flextasker.core.api.model.NetworkTaskList
import com.github.flextasker.core.model.TaskListInfo
import com.github.flextasker.core.model.TaskListType

fun TaskListInfo.asNetwork(): NetworkTaskList {
    return NetworkTaskList(
        id = id,
        name = name,
        type = type.asNetwork(),
    )
}

fun TaskListType.asNetwork(): Int {
    return when (this) {
        TaskListType.USER -> NetworkTaskList.TYPE_USER
        else -> NetworkTaskList.TYPE_DEFAULT
    }
}

fun NetworkTaskList.asModel(): TaskListInfo {
    return TaskListInfo(
        id = id,
        name = name,
        type = type.networkTaskListTypeAsModel(),
    )
}

fun Int.networkTaskListTypeAsModel(): TaskListType {
    return when (this) {
        NetworkTaskList.TYPE_USER -> TaskListType.USER
        else -> TaskListType.DEFAULT
    }
}
