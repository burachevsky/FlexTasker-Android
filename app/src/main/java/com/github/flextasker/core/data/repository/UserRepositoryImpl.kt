package com.github.flextasker.core.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.github.flextasker.core.domain.repository.UserRepository
import com.github.flextasker.core.model.UserInfo
import com.github.flextasker.core.preferences.USER_PREFS_NAME
import com.github.flextasker.core.preferences.property
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    context: Context,
) : UserRepository {

    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        USER_PREFS_NAME,
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private var userId: Long by prefs.property(KEY_USER_ID, 0)
    private var userEmail: String by prefs.property(KEY_USER_EMAIL, "")
    private var userToken: String by prefs.property(KEY_USER_TOKEN, "")

    override suspend fun getCurrentUserInfo(): UserInfo? {
        val id = userId
        val email = userEmail
        val token = userToken

        if (id < 0 || email.isEmpty() || token.isEmpty())
            return null

        return UserInfo(
            id = id,
            email = email,
            token = token,
        )
    }

    override suspend fun signIn(email: String, password: String) {
        //todo: wait for api
        userId = 1
        userEmail = email
        userToken = "123"
    }

    override suspend fun signUp(email: String, password: String, confirmPassword: String) {
        //todo: wait for api
        userId = 1
        userEmail = email
        userToken = "123"
    }

    override suspend fun logout() {
        userId = 0
        userEmail = ""
        userToken = ""
    }

    companion object {
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_TOKEN = "user_token"
    }
}