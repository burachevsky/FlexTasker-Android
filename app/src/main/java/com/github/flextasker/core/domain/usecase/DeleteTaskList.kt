package com.github.flextasker.core.domain.usecase

import com.github.flextasker.core.domain.repository.TaskListRepository
import javax.inject.Inject

class DeleteTaskList @Inject constructor(
    private val taskListRepository: TaskListRepository
) {

    suspend operator fun invoke(id: Long) {
        taskListRepository.deleteTaskList(id)
    }
}