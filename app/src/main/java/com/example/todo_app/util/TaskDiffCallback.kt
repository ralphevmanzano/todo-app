package com.example.todo_app.util

import androidx.recyclerview.widget.DiffUtil
import com.example.todo_app.data.Task

class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
  override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
    return oldItem.id == newItem.id
  }

  override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
    return oldItem == newItem
  }
}