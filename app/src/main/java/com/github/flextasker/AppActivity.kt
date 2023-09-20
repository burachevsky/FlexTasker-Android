package com.github.flextasker

import android.app.UiModeManager
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.getSystemService
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.flextasker.core.domain.usecase.settings.Theme
import com.github.flextasker.core.eventbus.AppEvent
import com.github.flextasker.core.eventbus.AppEventHandler
import com.github.flextasker.core.ui.container.NavDestinationMapper
import com.github.flextasker.core.ui.container.SystemBarsSizeProvider
import com.github.flextasker.core.ui.container.ViewController
import com.github.flextasker.core.ui.container.viewContainer
import com.github.flextasker.core.ui.di.ViewModelFactory
import com.github.flextasker.core.ui.event.SwitchTheme
import com.github.flextasker.core.ui.ext.getNavigationBarHeightFromSystemAttribute
import com.github.flextasker.core.ui.ext.getStatusBarHeightFromSystemAttribute
import com.github.flextasker.core.ui.navigation.NavControllerProvider
import com.github.flextasker.databinding.ActivityAppBinding
import com.google.android.material.color.DynamicColors
import javax.inject.Inject

class AppActivity : AppCompatActivity(),
    ViewController<AppViewModel>, AppEventHandler,
    SystemBarsSizeProvider by AppSystemBarsSizeProvider,
    NavControllerProvider, NavDestinationMapper.Provider {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<AppViewModel>

    override val binding by viewBinding(ActivityAppBinding::bind, R.id.appContainer)
    override val viewModel by viewModels<AppViewModel> { viewModelFactory }
    override val container by viewContainer()

    private var contentIsSet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        setupActivityAppearance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)

        if (AppSystemBarsSizeProvider.isInitialized) {
            setContent()
        } else {
            initializeSystemBarsAndSetContent()
        }
    }

    private fun initializeSystemBarsAndSetContent() {
        binding.root.rootView.setOnApplyWindowInsetsListener { _, insets ->
            if (contentIsSet)
                return@setOnApplyWindowInsetsListener insets

            if (!AppSystemBarsSizeProvider.isInitialized) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())

                    val navigationBarInsets = insets.getInsets(
                        WindowInsetsCompat.Type.navigationBars()
                    )

                    statusBarHeight = statusBarInsets.top
                    navigationBarHeight = navigationBarInsets.bottom
                } else {
                    statusBarHeight = getStatusBarHeightFromSystemAttribute()
                    navigationBarHeight = getNavigationBarHeightFromSystemAttribute()
                }

                AppSystemBarsSizeProvider.isInitialized = true
            }

            setContent()

            insets
        }
    }

    private fun setContent() {
        contentIsSet = true
        provideNavController().setGraph(R.navigation.app_graph)
    }

    override fun provideNavController(): NavController {
        return (supportFragmentManager.findFragmentById(R.id.appContainer) as NavHostFragment)
            .navController
    }

    override fun provideNavDestinationMapper(): NavDestinationMapper {
        return AppNavDestinationMapper
    }

    override fun handleEvent(event: AppEvent): Boolean {
        when (event) {
            is SwitchTheme -> {
                viewModel.themeIsInitialized = false
                recreate()
                return true
            }
        }

        return false
    }
    private fun setupActivityAppearance() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        val themeId = viewModel.getTheme()
        val dynamicColorsEnabled = viewModel.dynamicColorsEnabled()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            AppCompatDelegate.setDefaultNightMode(
                when (themeId) {
                    Theme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                    Theme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
                    else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                }
            )
        } else {
            getSystemService<UiModeManager>()?.nightMode = when (themeId) {
                Theme.LIGHT -> UiModeManager.MODE_NIGHT_NO
                Theme.DARK -> UiModeManager.MODE_NIGHT_YES
                else -> UiModeManager.MODE_NIGHT_AUTO
            }
        }

        if (DynamicColors.isDynamicColorAvailable()) {
            setTheme(
                when {
                    dynamicColorsEnabled -> R.style.Theme_FlexTasker
                    else -> R.style.Theme_FlexTasker_NoDynamicColors
                }
            )

            if (!viewModel.themeIsInitialized) {
                viewModel.themeIsInitialized = true

                if (themeId != Theme.SYSTEM && dynamicColorsEnabled) {
                    recreate()
                }
            }
        }
    }
}