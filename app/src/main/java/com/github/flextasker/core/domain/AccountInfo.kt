package com.github.flextasker.core.domain

interface AccountInfo {
    val userName: String
    val token: String

    fun update(userName: String, token: String)

    fun clear() = update("", "")
    fun isUserSignedIn() = token.isNotEmpty()
}