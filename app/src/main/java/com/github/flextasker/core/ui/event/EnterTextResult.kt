package com.github.flextasker.core.ui.event

import com.github.flextasker.core.eventbus.AppEvent

data class EnterTextResult(
    val actionId: Int,
    val enteredText: String,
) : AppEvent

object EnterTextAction {
    const val NEW_LIST = 0
}
