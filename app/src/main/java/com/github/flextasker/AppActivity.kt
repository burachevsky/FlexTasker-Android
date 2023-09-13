package com.github.flextasker

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.flextasker.core.eventbus.AppEvent
import com.github.flextasker.core.eventbus.AppEventHandler
import com.github.flextasker.core.ui.container.NavDestinationMapper
import com.github.flextasker.core.ui.container.SystemBarsSizeProvider
import com.github.flextasker.core.ui.container.ViewController
import com.github.flextasker.core.ui.container.viewContainer
import com.github.flextasker.core.ui.di.ViewModelFactory
import com.github.flextasker.core.ui.ext.getNavigationBarHeightFromSystemAttribute
import com.github.flextasker.core.ui.ext.getStatusBarHeightFromSystemAttribute
import com.github.flextasker.core.ui.navigation.NavControllerProvider
import com.github.flextasker.databinding.ActivityAppBinding
import javax.inject.Inject

class AppActivity : AppCompatActivity(),
    ViewController<AppViewModel>, AppEventHandler,
    SystemBarsSizeProvider by AppSystemBarsSizeProvider,
    NavControllerProvider, NavDestinationMapper.Provider {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<AppViewModel>

    override val binding by viewBinding(ActivityAppBinding::bind, R.id.appContainer)
    override val viewModel: AppViewModel by viewModels { viewModelFactory }
    override val container by viewContainer()

    private var contentIsSet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
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
        return false
    }
}