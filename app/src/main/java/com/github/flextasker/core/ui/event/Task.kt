package com.github.flextasker.core.ui.event

import com.github.flextasker.core.eventbus.AppEvent
import com.github.flextasker.core.model.Task

interface TaskEvent : AppEvent

data class TaskAdded(val task: Task): TaskEvent
data class TaskUpdated(val task: Task): TaskEvent
data class TaskDeleted(val id: Long): TaskEvent