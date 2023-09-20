package com.github.flextasker.core.domain.usecase.task

import com.github.flextasker.core.domain.repository.TaskRepository
import com.github.flextasker.core.model.Task
import javax.inject.Inject

class EditTask @Inject constructor(
    private val taskRepository: TaskRepository,
) {

    suspend operator fun invoke(task: Task) {
        return taskRepository.editTask(task)
    }
}