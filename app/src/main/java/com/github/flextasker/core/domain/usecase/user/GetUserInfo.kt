package com.github.flextasker.core.domain.usecase.user

import com.github.flextasker.core.domain.repository.UserRepository
import com.github.flextasker.core.model.UserInfo
import javax.inject.Inject

class GetUserInfo @Inject constructor(
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(): UserInfo? {
        return userRepository.getCurrentUserInfo()
    }
}