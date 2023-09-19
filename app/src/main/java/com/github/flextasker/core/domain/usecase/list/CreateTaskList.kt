package com.github.flextasker.core.domain.usecase.list

import com.github.flextasker.core.domain.repository.TaskListRepository
import com.github.flextasker.core.model.TaskListInfo
import javax.inject.Inject

class CreateTaskList @Inject constructor(
    private val taskListRepository: TaskListRepository,
) {

    suspend operator fun invoke(list: TaskListInfo): TaskListInfo {
        return taskListRepository.createTaskList(list)
    }
}