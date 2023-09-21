package com.github.flextasker.core.preferences

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty

inline fun <reified T> SharedPreferences.property(
    key: String,
    defaultValue: T? = null
): ReadWriteProperty<Any, T> {
    return SharedPreferencesProperty(this, key, defaultValue)
}