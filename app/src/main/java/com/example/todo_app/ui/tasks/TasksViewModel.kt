package com.example.todo_app.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.architecture.blueprints.todoapp.data.Result.Success
import com.example.todo_app.R
import com.example.todo_app.data.Task
import com.example.todo_app.data.TasksRepository
import com.example.todo_app.util.Event
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class TasksViewModel @Inject constructor(
  private val repository: TasksRepository,
  @Named("main") private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

  private val _tasks = MutableLiveData<List<Task>>().apply { value = emptyList() }
  val tasks: LiveData<List<Task>> = _tasks

  private val _newTaskEvent = MutableLiveData<Event<Unit>>()
  val newTaskEvent: LiveData<Event<Unit>> = _newTaskEvent

  private val _detailsEvent = MutableLiveData<Event<String>>()
  val detailsEvent: LiveData<Event<String>> = _detailsEvent

  private val _snackbarText = MutableLiveData<Event<Int>>()
  val snackbarText: LiveData<Event<Int>> = _snackbarText

  fun loadTasks() {
    viewModelScope.launch(mainDispatcher) {
      val tasksResult = repository.getTasks()
      if (tasksResult is Success) {
        val tasks = tasksResult.data
        _tasks.value = ArrayList(tasks)
      }
    }
  }

  fun completeTask(task: Task, isCompleted: Boolean) = viewModelScope.launch(mainDispatcher) {
    if (isCompleted) {
      repository.completeTask(task)
      showSnackbarMessage(R.string.successfully_marked)
    } else {
      repository.activateTask(task)
    }
    loadTasks()
  }

  fun deleteTask(id: String) = viewModelScope.launch(mainDispatcher) {
    repository.deleteTask(id)
    loadTasks()
  }

  fun goToNewTask() {
    _newTaskEvent.value = Event(Unit)
  }

  fun goToDetails(id: String) {
    _detailsEvent.value = Event(id)
  }

  private fun showSnackbarMessage(message: Int) {
    _snackbarText.value = Event(message)
  }
}
