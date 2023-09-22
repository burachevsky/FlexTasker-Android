package com.github.flextasker.feature.auth.signup

import androidx.lifecycle.ViewModel
import com.github.flextasker.R
import com.github.flextasker.core.domain.exception.ApiException
import com.github.flextasker.core.domain.usecase.user.SignUp
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

class SignUpViewModel @Inject constructor(
    private val signUp: SignUp,
    private val eventBus: EventBus,
) : ViewModel(), VM<AuthNavigator> {

    override val container = viewModelContainer()

    private val _error = MutableStateFlow<Txt?>(null)
    val error = _error.asStateFlow()

    var email = ""
    var password = ""
    var confirmPassword = ""

    fun signUpClicked() {
        if (validate()) {
            container.launch(Dispatchers.Main) {
                try {
                    signUp(email, password, confirmPassword)
                    eventBus.send(UserChanged)
                } catch (e: ApiException) {
                    _error.emit(Txt.of(e.message))
                }
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

        if (password != confirmPassword) {
            _error.value = Txt.of(R.string.passwords_are_not_the_same)
            return false
        }

        return true
    }
}