package com.github.flextasker.core.domain.usecase.user

import com.github.flextasker.core.domain.repository.UserRepository
import javax.inject.Inject

class SignIn @Inject constructor(
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(email: String, password: String) {
        userRepository.signIn(email, password)
    }
}