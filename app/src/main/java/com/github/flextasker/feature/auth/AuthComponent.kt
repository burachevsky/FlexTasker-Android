package com.github.flextasker.feature.auth

import com.github.flextasker.feature.auth.signin.SignInFragment
import com.github.flextasker.feature.auth.signup.SignUpFragment
import dagger.Subcomponent

@Subcomponent
interface AuthComponent {

    fun inject(fragment: SignInFragment)
    fun inject(fragment: SignUpFragment)

    interface Provider {

        fun authComponent(): AuthComponent
    }
}