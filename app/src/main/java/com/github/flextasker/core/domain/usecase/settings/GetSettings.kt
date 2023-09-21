package com.github.flextasker.core.domain.usecase.settings

import com.github.flextasker.core.data.SettingsImpl
import com.github.flextasker.core.model.Settings
import javax.inject.Inject

class GetSettings @Inject constructor(
    private val settingsImpl: SettingsImpl
) {

    operator fun invoke(): Settings {
        return settingsImpl
    }
}