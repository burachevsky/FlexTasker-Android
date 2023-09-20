package com.github.flextasker.core.domain.repository

import com.github.flextasker.core.model.UserInfo

interface UserRepository {

    suspend fun getCurrentUserInfo(): UserInfo?
}