package com.github.flextasker.feature.taskdetails

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.core.widget.addTextChangedListener
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
import com.github.flextasker.databinding.FragmentTaskDetailsBinding
import javax.inject.Inject

class TaskDetailsFragment : Fragment(R.layout.fragment_task_details),
    ViewController<TaskDetailsViewModel> {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<TaskDetailsViewModel>

    override val binding by viewBinding(FragmentTaskDetailsBinding::bind)
    override val viewModel by viewModels<TaskDetailsViewModel> { viewModelFactory }
    override val container by viewContainer()

    private val nameListener: (Editable?) -> Unit = {
        viewModel.nameChanged(it?.toString().orEmpty())
    }

    private val descriptionListener: (Editable?) -> Unit = {
        viewModel.descriptionChanged(it?.toString().orEmpty())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationAs<TaskDetailsComponent.Provider>()
            .taskDetailsComponent(TaskDetailsModule(this))
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        collectOnStarted(viewModel.name) {
            binding.taskEditText.apply {
                if (text.toString() != it) {
                    setText(it)
                }
            }
        }
        binding.taskEditText.addTextChangedListener(afterTextChanged = nameListener)

        collectOnStarted(viewModel.description) {
            binding.descriptionEditText.apply {
                if (text.toString() != it) {
                    setText(it)
                }
            }
        }
        binding.descriptionEditText.addTextChangedListener(afterTextChanged = descriptionListener)

        collectOnStarted(viewModel.isStarred) { isSelected ->
            isSelected?.let(binding.starButton::setSelected)
        }

        binding.starButton.setOnClickListener {
            viewModel.starClicked()
        }

        collectOnStarted(viewModel.listName) {
            binding.label.text = it
        }

        binding.deleteButton.setOnClickListener {
            viewModel.deleteClicked()
        }
    }
}
