package com.github.flextasker.core.domain.usecase

import com.github.flextasker.core.domain.repository.TaskListRepository
import com.github.flextasker.core.model.TaskListInfo
import javax.inject.Inject

class GetTaskLists @Inject constructor(
    private val taskListRepository: TaskListRepository,
) {

    suspend operator fun invoke(): List<TaskListInfo> {
        return taskListRepository.getAllTaskLists()
    }
}