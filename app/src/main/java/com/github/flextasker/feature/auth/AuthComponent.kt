package com.github.flextasker.feature.auth

import com.github.flextasker.feature.auth.signin.SignInFragment
import dagger.Subcomponent

@Subcomponent
interface AuthComponent {

    fun inject(fragment: SignInFragment)

    interface Provider {

        fun authComponent(): AuthComponent
    }
}