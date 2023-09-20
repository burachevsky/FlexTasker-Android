package com.github.flextasker.feature.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.flextasker.R
import com.github.flextasker.core.ui.container.ViewController
import com.github.flextasker.core.ui.container.viewContainer
import com.github.flextasker.core.ui.di.ViewModelFactory
import com.github.flextasker.core.ui.ext.applicationAs
import com.github.flextasker.core.ui.ext.collectOnStarted
import com.github.flextasker.core.ui.ext.verticalLinearLayoutManager
import com.github.flextasker.core.ui.recycler.CompositeAdapter
import com.github.flextasker.core.ui.utils.EmptyItemAdapter
import com.github.flextasker.core.ui.utils.SubtitleItemAdapter
import com.github.flextasker.core.ui.utils.SwitchItemAdapter
import com.github.flextasker.core.ui.utils.TextItemAdapter
import com.github.flextasker.core.ui.utils.ToggleGroupItem
import com.github.flextasker.core.ui.utils.ToggleGroupItemItemAdapter
import com.github.flextasker.databinding.FragmentSettingsBinding
import com.github.flextasker.feature.settings.item.LogoutItem
import com.github.flextasker.feature.settings.item.LogoutItemAdapter
import javax.inject.Inject

class SettingsFragment : Fragment(R.layout.fragment_settings), ViewController<SettingsViewModel> {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<SettingsViewModel>

    override val binding by viewBinding(FragmentSettingsBinding::bind)
    override val viewModel by viewModels<SettingsViewModel> { viewModelFactory }
    override val container by viewContainer()

    private val listAdapter = CompositeAdapter(
        SwitchItemAdapter(),
        SubtitleItemAdapter(),
        TextItemAdapter(),
        EmptyItemAdapter(),
        ToggleGroupItemItemAdapter(
            object : ToggleGroupItem.Listener {
                override fun onSelectionChanged(position: Int) {
                    viewModel.toggleGroupSelectionChanged(position)
                }
            }
        ),
        LogoutItemAdapter(
            object : LogoutItem.Listener {
                override fun onClick() {
                    viewModel.logout()
                }
            }
        )
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationAs<SettingsComponent.Provider>().settingsComponent()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.apply {
            layoutManager = verticalLinearLayoutManager()
            adapter = listAdapter
            setHasFixedSize(true)
        }

        collectOnStarted(viewModel.items, listAdapter::submitList)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}