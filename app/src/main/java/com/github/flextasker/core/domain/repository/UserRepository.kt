package com.github.flextasker.core.domain.repository

interface UserRepository {

    suspend fun signIn(email: String, password: String)

    suspend fun signUp(email: String, password: String, confirmPassword: String)

    suspend fun logout()
}