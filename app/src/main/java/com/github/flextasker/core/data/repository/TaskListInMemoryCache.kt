package com.github.flextasker.core.data.repository

import com.github.flextasker.core.model.TaskListInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskListInMemoryCache @Inject constructor() {

    val map = HashMap<Long, TaskListInfo>()
}