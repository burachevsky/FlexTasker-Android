package com.github.flextasker.core.domain.usecase.user

import com.github.flextasker.core.domain.repository.UserRepository
import javax.inject.Inject

class Logout @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke() {
        userRepository.logout()
    }
}