package com.example.todo_app.ui.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_app.data.Task
import com.example.todo_app.databinding.TaskItemBinding
import com.example.todo_app.util.TaskDiffCallback

class TasksAdapter(private val viewModel: TasksViewModel) : ListAdapter<Task, TasksAdapter.ViewHolder>(TaskDiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder.from(parent)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = getItem(position)
    holder.bind(viewModel, item)
  }

  class ViewHolder private constructor(val binding: TaskItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
      fun from(parent: ViewGroup) : ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
      }
    }

    fun bind(viewModel: TasksViewModel, task: Task) {
      binding.viewModel = viewModel
      binding.task = task
      binding.executePendingBindings()
    }
  }
}