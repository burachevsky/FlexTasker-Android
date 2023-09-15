package com.github.flextasker.core.domain.usecase

import com.github.flextasker.core.domain.repository.TaskRepository
import com.github.flextasker.core.model.Task
import javax.inject.Inject

class GetTasks @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(listId: Long?, filterStarred: Boolean): List<Task> {
        return taskRepository.getTasks(listId, filterStarred)
    }
}