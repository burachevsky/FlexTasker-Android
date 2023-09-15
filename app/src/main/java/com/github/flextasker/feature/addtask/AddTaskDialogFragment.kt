package com.github.flextasker.feature.addtask

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
import android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
import android.view.inputmethod.EditorInfo
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import com.github.flextasker.core.ui.container.DependentOnSystemBarsSize
import com.github.flextasker.core.ui.container.ViewController
import com.github.flextasker.core.ui.container.viewContainer
import com.github.flextasker.core.ui.di.ViewModelFactory
import com.github.flextasker.core.ui.ext.applicationAs
import com.github.flextasker.databinding.DialogAddTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.elevation.SurfaceColors
import javax.inject.Inject

class AddTaskDialogFragment : BottomSheetDialogFragment(),
    ViewController<AddTaskViewModel>, DependentOnSystemBarsSize {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<AddTaskViewModel>

    private var _binding: DialogAddTaskBinding? = null
    override val binding get() = _binding!!
    override val viewModel by viewModels<AddTaskViewModel> { viewModelFactory }
    override val container by viewContainer()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationAs<AddTaskComponent.Provider>()
            .addTaskComponent(AddTaskModule(this))
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddTaskBinding.inflate(LayoutInflater.from(requireActivity()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        @Suppress("DEPRECATION")
        dialog?.window?.setSoftInputMode(SOFT_INPUT_STATE_VISIBLE or SOFT_INPUT_ADJUST_RESIZE)

        binding.rootLayout.setBackgroundColor(
            SurfaceColors.SURFACE_2.getColor(requireActivity())
        )

        binding.taskEditText.apply {
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    submit()
                    true
                } else false
            }
        }

        binding.saveButton.setOnClickListener {
            submit()
        }

        binding.star.apply {
            setOnClickListener {
                isSelected = !isSelected
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.taskEditText.requestFocus()
    }

    override fun fitSystemBars(statusBarHeight: Int, navigationBarHeight: Int) {
        binding.rootLayout.updatePadding(bottom = -navigationBarHeight)
    }

    private fun submit() {
        viewModel.submit(
            name = binding.taskEditText.text.toString(),
            description = binding.descriptionEditText.text.toString(),
            isStarred = binding.star.isSelected,
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}