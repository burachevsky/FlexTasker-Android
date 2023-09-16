package com.github.flextasker.feature.entertext

import androidx.lifecycle.ViewModel
import com.github.flextasker.core.eventbus.EventBus
import com.github.flextasker.core.ui.container.VM
import com.github.flextasker.core.ui.container.viewModelContainer
import com.github.flextasker.core.ui.event.EnterTextResult
import com.github.flextasker.core.ui.navigation.Navigator
import com.github.flextasker.core.ui.text.ParcelableTxt
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Named

class EnterTextViewModel @Inject constructor(
    private val eventBus: EventBus,
    @Named(TITLE_ARG) val title: ParcelableTxt,
    @Named(INIT_TEXT_ARG) val initText: ParcelableTxt?,
    private val actionId: Int,
) : ViewModel(), VM<Navigator> {

    override val container = viewModelContainer()

    fun sendResult(text: String) {
        container.launch(Dispatchers.Main) {
            eventBus.send(EnterTextResult(actionId, text))

            container.navigator {
                back()
            }
        }
    }
}
