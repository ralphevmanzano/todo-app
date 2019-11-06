package com.example.todo_app.ui.addtask

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todo_app.R
import com.example.todo_app.databinding.AddTaskFragmentBinding
import com.example.todo_app.ui.BaseFragment
import com.example.todo_app.util.EventObserver

class AddTaskFragment : BaseFragment<AddTaskViewModel, AddTaskFragmentBinding>() {

  private val args: AddTaskFragmentArgs by navArgs()

  override fun getViewModel(): Class<AddTaskViewModel> {
    return AddTaskViewModel::class.java
  }

  override fun getLayoutRes(): Int {
    return R.layout.add_task_fragment
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setupNavigation()
    viewModel.init(args.id)
  }

  override fun onStop() {
    hideKeyboard()
    super.onStop()
  }

  private fun setupNavigation() {
    viewModel.taskUpdatedEvent.observe(viewLifecycleOwner, EventObserver{
      findNavController().popBackStack()
    })
  }

}
