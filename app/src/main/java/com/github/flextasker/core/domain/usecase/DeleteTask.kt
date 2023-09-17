package com.github.flextasker.core.domain.usecase

import com.github.flextasker.core.domain.repository.TaskRepository
import javax.inject.Inject

class DeleteTask @Inject constructor(
    private val taskRepository: TaskRepository,
) {

    suspend operator fun invoke(id: Long) {
        taskRepository.deleteTask(id)
    }
}