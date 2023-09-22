package com.github.flextasker.core.data.repository

import com.github.flextasker.core.api.AccountApi
import com.github.flextasker.core.domain.AccountInfo
import com.github.flextasker.core.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val accountInfo: AccountInfo,
    private val api: AccountApi,
) : UserRepository {

    override suspend fun signIn(email: String, password: String) {
        api.signIn(email, password)
            .also { accountInfo.update(it.userName, it.token) }
    }

    override suspend fun signUp(email: String, password: String, confirmPassword: String) {
        api.signUp(email, password, confirmPassword)
            .also { accountInfo.update(it.userName, it.token) }
    }

    override suspend fun logout() {
        api.logout()
        accountInfo.clear()
    }
}