package com.example.todo_app.util

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_app.data.Task
import com.example.todo_app.ui.tasks.TasksAdapter

@BindingAdapter("app:tasks")
fun setTasks(listView: RecyclerView, items: List<Task>) {
  (listView.adapter as TasksAdapter).submitList(items)
}

@BindingAdapter("app:completedTask")
fun setStyle(textView: TextView, enabled: Boolean) {
  if (enabled) {
    textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
  } else {
    textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
  }
}