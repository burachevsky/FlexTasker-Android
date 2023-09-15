package com.github.flextasker.core.domain.usecase

import com.github.flextasker.core.domain.repository.TaskRepository
import com.github.flextasker.core.model.Task
import javax.inject.Inject

class AddTask @Inject constructor(
    private val taskRepository: TaskRepository,
) {

    suspend operator fun invoke(task: Task): Task {
        return taskRepository.addTask(task)
    }
}