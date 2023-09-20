package com.github.flextasker.feature.taskdetails

import kotlinx.coroutines.Job

data class Updater(
    val action: suspend () -> Unit,
    val launchMillis: Long,
) {
    var job: Job? = null
}
