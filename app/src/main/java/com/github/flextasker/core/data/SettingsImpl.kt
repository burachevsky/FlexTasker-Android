package com.github.flextasker.core.data

import android.content.Context
import android.content.SharedPreferences
import com.github.flextasker.core.domain.usecase.settings.Theme
import com.github.flextasker.core.model.Settings
import com.github.flextasker.core.preferences.APP_PREFS_NAME
import com.github.flextasker.core.preferences.property
import javax.inject.Inject

class SettingsImpl @Inject constructor(
    context: Context
) : Settings {

    private val prefs: SharedPreferences = context
        .getSharedPreferences(APP_PREFS_NAME, Context.MODE_PRIVATE)

    override var dynamicColorsEnabled by prefs.property(KEY_DYNAMIC_COLORS_ENABLED, true)

    override var theme by prefs.property(KEY_THEME, Theme.SYSTEM)

    companion object {
        private const val KEY_DYNAMIC_COLORS_ENABLED = "dynamic_colors_enabled"
        private const val KEY_THEME = "theme"
    }
}