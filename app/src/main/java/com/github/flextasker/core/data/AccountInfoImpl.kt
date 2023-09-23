package com.github.flextasker.core.data

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.github.flextasker.core.domain.AccountInfo
import com.github.flextasker.core.preferences.USER_PREFS_NAME
import com.github.flextasker.core.preferences.property
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountInfoImpl @Inject constructor(
    context: Context,
) : AccountInfo {

    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        USER_PREFS_NAME,
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override var userName: String by prefs.property(KEY_USER_NAME, "")
        private set

    override var token: String by prefs.property(KEY_USER_TOKEN, "")
        private set

    override fun update(userName: String, token: String) {
        this.userName = userName
        this.token = token
    }

    companion object {
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_TOKEN = "user_token"
    }
}