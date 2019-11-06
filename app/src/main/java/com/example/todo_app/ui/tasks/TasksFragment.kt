package com.example.todo_app.ui.tasks

import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.todo_app.R
import com.example.todo_app.databinding.TasksFragmentBinding
import com.example.todo_app.ui.BaseFragment
import com.example.todo_app.util.EventObserver
import kotlinx.android.synthetic.main.tasks_fragment.*

class TasksFragment : BaseFragment<TasksViewModel, TasksFragmentBinding>() {

  override fun getViewModel(): Class<TasksViewModel> {
    return TasksViewModel::class.java
  }

  override fun getLayoutRes(): Int {
    return R.layout.tasks_fragment
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    setupList()
    setupNavigation()
    setupFab()
    viewModel.loadTasks()
  }

  private fun setupList() {
    val adapter = TasksAdapter(viewModel)
    binding.rv.adapter = adapter
  }

  private fun setupNavigation() {
    viewModel.newTaskEvent.observe(viewLifecycleOwner, EventObserver {
      goToNewTask()
    })

    viewModel.detailsEvent.observe(viewLifecycleOwner, EventObserver {
      goToDetails(it)
    })
  }

  private fun setupFab() {
    fab?.apply {
      setOnClickListener {
        viewModel.goToNewTask()
      }
    }
  }

  private fun goToNewTask() {
    val action = TasksFragmentDirections.tasksToAddTasks(null)
    findNavController().navigate(action)
  }

  private fun goToDetails(id: String) {
    val action = TasksFragmentDirections.tasksToAddTasks(id)
    findNavController().navigate(action)
  }

}
