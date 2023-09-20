package com.github.flextasker.feature.settings

import androidx.lifecycle.ViewModel
import com.github.flextasker.R
import com.github.flextasker.core.domain.usecase.settings.GetSettings
import com.github.flextasker.core.domain.usecase.settings.Theme
import com.github.flextasker.core.eventbus.EventBus
import com.github.flextasker.core.ui.container.VM
import com.github.flextasker.core.ui.container.viewModelContainer
import com.github.flextasker.core.ui.event.SwitchTheme
import com.github.flextasker.core.ui.ext.get
import com.github.flextasker.core.ui.navigation.Navigator
import com.github.flextasker.core.ui.recycler.ListItem
import com.github.flextasker.core.ui.text.Txt
import com.github.flextasker.core.ui.text.of
import com.github.flextasker.core.ui.utils.SubtitleItem
import com.github.flextasker.core.ui.utils.SwitchItem
import com.github.flextasker.core.ui.utils.ToggleGroupItem
import com.github.flextasker.core.ui.utils.ToggleOption
import com.google.android.material.color.DynamicColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    getSettings: GetSettings,
    private val eventBus: EventBus,
) : ViewModel(), VM<Navigator> {

    override val container = viewModelContainer()

    private val _items: MutableStateFlow<List<ListItem>> = MutableStateFlow(emptyList())
    val items: StateFlow<List<ListItem>> = _items

    private val settings = getSettings()

    fun toggleGroupSelectionChanged(position: Int) {
        val item = items.get<ToggleGroupItem>(position)

        when (item.id) {
            THEME_SELECTOR_ID -> {
                settings.theme = item.selectedValue
                switchTheme()
            }
        }
    }

    fun recreate() {
        _items.update { list() }
    }

    private fun list(): List<ListItem> {
        return listOfNotNull(
            SubtitleItem(Txt.of(R.string.settings_appearance)),
            ToggleGroupItem(
                title = Txt.of(R.string.settings_theme),
                options = listOf(
                    ToggleOption(
                        id = Theme.SYSTEM,
                        text = Txt.of(R.string.settings_theme_system)
                    ),
                    ToggleOption(
                        id = Theme.LIGHT,
                        text = Txt.of(R.string.settings_theme_light)
                    ),
                    ToggleOption(
                        id = Theme.DARK,
                        text = Txt.of(R.string.settings_theme_dark)
                    )
                ),
                selectedValue = settings.theme,
                marginTopRes = R.dimen.toggle_group_margin_top_small,
                isVertical = false,
            ),
            when {
                DynamicColors.isDynamicColorAvailable() -> SwitchItem(
                    text = Txt.of(R.string.settings_dynamic_colors),
                    marginTopRes = R.dimen.switch_item_margin_top_small,
                    isChecked = settings.dynamicColorsEnabled,
                    onCheckChanged = {
                        settings.dynamicColorsEnabled = it
                        switchTheme()
                    }
                )

                else -> null
            },

            )
    }

    private fun switchTheme() {
        container.launch(Dispatchers.Main) {
            eventBus.send(SwitchTheme)
        }
    }

    companion object {
        private const val THEME_SELECTOR_ID = 0
    }
}