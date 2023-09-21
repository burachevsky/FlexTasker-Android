package com.github.flextasker.feature.auth.signin

import androidx.lifecycle.ViewModel
import com.github.flextasker.R
import com.github.flextasker.core.domain.usecase.user.SignIn
import com.github.flextasker.core.eventbus.EventBus
import com.github.flextasker.core.ui.container.VM
import com.github.flextasker.core.ui.container.viewModelContainer
import com.github.flextasker.core.ui.event.UserChanged
import com.github.flextasker.core.ui.text.Txt
import com.github.flextasker.core.ui.text.of
import com.github.flextasker.core.ui.utils.isValidEmail
import com.github.flextasker.feature.auth.AuthNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val signIn: SignIn,
    private val eventBus: EventBus,
) : ViewModel(), VM<AuthNavigator> {

    override val container = viewModelContainer()

    private val _error = MutableStateFlow<Txt?>(null)
    val error = _error.asStateFlow()

    var email = ""
    var password = ""

    fun navigateSignUp() {
        container.navigator {
            navigateSignUp()
        }
    }

    fun signInClicked() {
        if (validate()) {
            container.launch(Dispatchers.IO) {
                signIn(email, password)
                eventBus.send(UserChanged)
            }
        }
    }

    fun clearErrors() {
        _error.value = null
    }

    private fun validate(): Boolean {
        if (!email.isValidEmail()) {
            _error.value = Txt.of(R.string.invalid_email_format)
            return false
        }

        if (password.isEmpty()) {
            _error.value = Txt.of(R.string.empty_password_error)
            return false
        }

        return true
    }
}