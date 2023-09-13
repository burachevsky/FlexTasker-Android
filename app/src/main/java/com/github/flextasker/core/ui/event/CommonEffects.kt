package com.github.flextasker.core.ui.event

import com.github.flextasker.core.eventbus.AppEvent
import com.github.flextasker.core.ui.navigation.Navigator
import com.github.flextasker.core.ui.text.Txt

data class ToastMessage(
    val text: Txt
) : AppEvent

data class Navigate(
    val navigateAction: (Navigator) -> Unit
) : AppEvent

data class AlertDialog(
    val title: Txt? = null,
    val message: Txt? = null,
    val yes: Button? = null,
    val no: Button? = null,
    val cancel: Button? = null,
    val cancelable: Boolean = true
) : AppEvent {

    data class Button(
        val text: Txt,
        val action: (() -> Unit)? = null
    )
}

object SwitchTheme : AppEvent
