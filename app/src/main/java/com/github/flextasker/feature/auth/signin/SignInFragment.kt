package com.github.flextasker.feature.auth.signin

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.flextasker.R
import com.github.flextasker.core.ui.container.ViewController
import com.github.flextasker.core.ui.container.viewContainer
import com.github.flextasker.core.ui.di.ViewModelFactory
import com.github.flextasker.core.ui.ext.applicationAs
import com.github.flextasker.databinding.FragmentSignInBinding
import com.github.flextasker.feature.auth.AuthComponent
import javax.inject.Inject

class SignInFragment : Fragment(R.layout.fragment_sign_in), ViewController<SignInViewModel> {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<SignInViewModel>

    override val binding by viewBinding(FragmentSignInBinding::bind)
    override val viewModel by viewModels<SignInViewModel> { viewModelFactory }
    override val container by viewContainer()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationAs<AuthComponent.Provider>().authComponent()
            .inject(this)
    }
}