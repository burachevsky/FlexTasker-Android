package com.github.flextasker.core.domain.usecase.user

import com.github.flextasker.core.domain.repository.UserRepository
import javax.inject.Inject

class SignUp @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(email: String, password: String, confirmPassword: String) {
        userRepository.signUp(email, password, confirmPassword)
    }
}