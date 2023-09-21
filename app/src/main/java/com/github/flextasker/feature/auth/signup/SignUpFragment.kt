package com.github.flextasker.feature.auth.signup

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.flextasker.R
import com.github.flextasker.core.ui.container.ViewController
import com.github.flextasker.core.ui.container.viewContainer
import com.github.flextasker.core.ui.di.ViewModelFactory
import com.github.flextasker.core.ui.ext.applicationAs
import com.github.flextasker.core.ui.ext.collectOnStarted
import com.github.flextasker.databinding.FragmentSignUpBinding
import com.github.flextasker.feature.auth.AuthComponent
import javax.inject.Inject

class SignUpFragment : Fragment(R.layout.fragment_sign_up), ViewController<SignUpViewModel> {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<SignUpViewModel>

    override val binding by viewBinding(FragmentSignUpBinding::bind)
    override val viewModel by viewModels<SignUpViewModel> { viewModelFactory }
    override val container by viewContainer()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationAs<AuthComponent.Provider>().authComponent()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.emailEditText.addTextChangedListener {
            viewModel.email = it.toString()
            viewModel.clearErrors()
        }

        binding.passwordEditText.addTextChangedListener {
            viewModel.password = it.toString()
            viewModel.clearErrors()
        }

        binding.confirmPasswordEditText.addTextChangedListener {
            viewModel.confirmPassword = it.toString()
            viewModel.clearErrors()
        }

        collectOnStarted(viewModel.error) {
            binding.error.apply {
                text = it?.get(requireContext())
                isVisible = it != null
            }
        }

        binding.signUpButton.setOnClickListener {
            viewModel.signUpClicked()
        }
    }
}