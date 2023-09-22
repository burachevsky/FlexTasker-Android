package com.github.flextasker.core.ui.utils

import android.util.Patterns

fun String.isValidEmail(): Boolean {
    return !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}