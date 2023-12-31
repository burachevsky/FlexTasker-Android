package com.github.flextasker.core.ui.container

import com.github.flextasker.R
import com.github.flextasker.core.ui.event.Navigate
import com.github.flextasker.core.eventbus.AppEvent
import com.github.flextasker.core.ui.event.AlertDialog
import com.github.flextasker.core.ui.navigation.Navigator
import com.github.flextasker.core.ui.text.Txt
import com.github.flextasker.core.ui.text.of
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import timber.log.Timber
import kotlinx.coroutines.async as libAsync

class ViewModelContainer<N : Navigator>(
    val scope: CoroutineScope
) {
    private val _effect = MutableSharedFlow<AppEvent>()
    val effect: SharedFlow<AppEvent> = _effect

    fun raiseEffect(effect: AppEvent) {
        launch(Dispatchers.Main) {
            _effect.emit(effect)
        }
    }

    inline fun raiseEffect(effect: () -> AppEvent) {
        raiseEffect(effect())
    }

    @Suppress("UNCHECKED_CAST")
    inline fun navigator(crossinline navigatorAction: N.() -> Unit) {
        raiseEffect {
            Navigate {
                (it as N).navigatorAction()
            }
        }
    }

    inline fun <T> runSafely(action: () -> T): T? {
        return try {
            action()
        } catch (e: Exception) {
            Timber.e(e)
            raiseEffect {
                AlertDialog(
                    message = Txt.of(R.string.something_went_wrong),
                    yes = AlertDialog.Button(Txt.of(R.string.button_ok))
                )
            }
            null
        }
    }

    inline fun launch(
        dispatcher: CoroutineDispatcher,
        crossinline action: suspend CoroutineScope.() -> Unit
    ) = scope.launch(dispatcher) {
        runSafely { action() }
    }

    suspend inline fun <T> withContext(
        dispatcher: CoroutineDispatcher,
        crossinline action: suspend CoroutineScope.() -> T
    ): T? = kotlinx.coroutines.withContext(dispatcher) {
        runSafely { action() }
    }

    inline fun <T> async(
        dispatcher: CoroutineDispatcher,
        crossinline action: suspend CoroutineScope.() -> T
    ): Deferred<T?> = scope.libAsync(dispatcher) {
        runSafely { action() }
    }
}