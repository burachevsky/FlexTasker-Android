package com.github.flextasker.core.domain.repository

import com.github.flextasker.core.model.UserInfo

interface UserRepository {

    suspend fun getCurrentUserInfo(): UserInfo?

    suspend fun signIn(email: String, password: String)

    suspend fun signUp(email: String, password: String, confirmPassword: String)

    suspend fun logout()
}