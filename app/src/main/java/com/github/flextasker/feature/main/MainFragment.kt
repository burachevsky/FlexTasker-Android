package com.github.flextasker.feature.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.flextasker.R
import com.github.flextasker.core.eventbus.AppEvent
import com.github.flextasker.core.eventbus.AppEventHandler
import com.github.flextasker.core.ui.container.DependentOnSystemBarsSize
import com.github.flextasker.core.ui.container.ViewController
import com.github.flextasker.core.ui.container.viewContainer
import com.github.flextasker.core.ui.di.ViewModelFactory
import com.github.flextasker.core.ui.ext.applicationAs
import com.github.flextasker.core.ui.ext.collectOnStarted
import com.github.flextasker.core.ui.ext.verticalLinearLayoutManager
import com.github.flextasker.core.ui.recycler.CompositeAdapter
import com.github.flextasker.core.ui.utils.EmptyItemAdapter
import com.github.flextasker.core.ui.utils.setupPullToRefresh
import com.github.flextasker.databinding.FragmentMainBinding
import com.github.flextasker.feature.main.item.DrawerMenuItem
import com.github.flextasker.feature.main.item.DrawerMenuItemAdapter
import com.github.flextasker.feature.main.item.TaskItem
import com.github.flextasker.feature.main.item.TaskItemAdapter
import com.google.android.material.elevation.SurfaceColors
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main),
    ViewController<MainViewModel>, DependentOnSystemBarsSize, AppEventHandler {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<MainViewModel>

    override val binding by viewBinding(FragmentMainBinding::bind)
    override val viewModel by viewModels<MainViewModel> { viewModelFactory }
    override val container by viewContainer()

    private val listAdapter = CompositeAdapter(
        TaskItemAdapter(
            object : TaskItem.Listener {
                override fun onClick(position: Int) {
                    viewModel.onTaskClick(position)
                }

                override fun onCompleteClick(position: Int) {
                    viewModel.onTaskCompleteClicked(position)
                }

                override fun onStarClick(position: Int) {
                    viewModel.onTaskStarClicked(position)
                }
            }
        )
    )

    private val drawerListAdapter = CompositeAdapter(
        DrawerMenuItemAdapter(
            object : DrawerMenuItem.Listener {
                override fun onClick(position: Int) {
                    viewModel.drawerMenuItemClicked(position)
                }
            },
        ),
        EmptyItemAdapter(),
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationAs<MainComponent.Provider>().mainComponent()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPullToRefresh(binding.swipeRefreshLayout, viewModel)

        binding.bottomAppBar.setBackgroundColor(SurfaceColors.SURFACE_2.getColor(requireContext()))

        binding.recyclerView.apply {
            layoutManager = verticalLinearLayoutManager()
            (itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
            adapter = listAdapter
            setHasFixedSize(true)
        }

        binding.drawerRecyclerView.apply {
            layoutManager = verticalLinearLayoutManager()
            (itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
            adapter = drawerListAdapter
            setHasFixedSize(true)
        }

        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        binding.bottomAppBarToolbar.setOnMenuItemClickListener {
           handleContextMenuAction(it.itemId)
        }

        collectOnStarted(viewModel.selectedListName) { text ->
            binding.toolbar.title = text?.get(requireContext())
        }

        collectOnStarted(viewModel.items, listAdapter::submitList)
        collectOnStarted(viewModel.drawerItems, drawerListAdapter::submitList)
    }

    override fun fitSystemBars(statusBarHeight: Int, navigationBarHeight: Int) {
        binding.drawerRecyclerView.updatePadding(top = statusBarHeight)
        binding.toolbarLayout.updatePadding(top = statusBarHeight)
        binding.bottomAppBar.updatePadding(bottom = navigationBarHeight)
    }

    override fun handleEvent(event: AppEvent): Boolean {
        when (event) {
            CloseDrawer -> {
                binding.drawerLayout.close()
            }
        }

        return false
    }

    private fun handleContextMenuAction(id: Int?): Boolean {
        when (id) {
            R.id.newTask -> viewModel.newTaskClicked()
        }
        return true
    }
}