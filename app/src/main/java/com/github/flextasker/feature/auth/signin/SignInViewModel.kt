package com.github.flextasker.feature.auth.signin

import androidx.lifecycle.ViewModel
import com.github.flextasker.core.ui.container.VM
import com.github.flextasker.core.ui.container.viewModelContainer
import com.github.flextasker.feature.auth.AuthNavigator
import javax.inject.Inject

class SignInViewModel @Inject constructor(

) : ViewModel(), VM<AuthNavigator> {

    override val container = viewModelContainer()


}