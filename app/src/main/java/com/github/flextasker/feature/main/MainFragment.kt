package com.github.flextasker.feature.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.flextasker.R
import com.github.flextasker.core.ui.container.ViewController
import com.github.flextasker.core.ui.container.viewContainer
import com.github.flextasker.core.ui.di.ViewModelFactory
import com.github.flextasker.core.ui.ext.applicationAs
import com.github.flextasker.databinding.FragmentMainBinding
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main),
    ViewController<MainViewModel> {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<MainViewModel>

    override val binding by viewBinding(FragmentMainBinding::bind)
    override val viewModel: MainViewModel by viewModels { viewModelFactory }
    override val container by viewContainer()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationAs<MainComponent.Provider>().mainComponent()
            .inject(this)
    }
}