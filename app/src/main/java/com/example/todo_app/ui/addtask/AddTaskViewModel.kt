package com.example.todo_app.ui.addtask

import android.util.Log
import androidx.lifecycle.*
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Result.*
import com.example.todo_app.data.Task
import com.example.todo_app.data.TasksRepository
import com.example.todo_app.ui.BaseViewModel
import com.example.todo_app.util.Event
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class AddTaskViewModel @Inject constructor(
  private val repository: TasksRepository,
  @Named("main") val dispatcher: CoroutineDispatcher
) : BaseViewModel() {

  val title = MutableLiveData<String>()
  val description = MutableLiveData<String>()
  var taskCompleted = false

  private val _taskUpdated = MutableLiveData<Event<Unit>>()
  val taskUpdatedEvent: LiveData<Event<Unit>> = _taskUpdated

  private val _dataLoading = MutableLiveData<Boolean>()
  val dataLoading: LiveData<Boolean> = _dataLoading

  private var taskId: String? = null
  private var isNewTask: Boolean = false

  fun init(taskId: String?) {
    if (_dataLoading.value == true) return

    this.taskId = taskId
    if (taskId == null) {
      isNewTask = true
      return
    }

    isNewTask = false
    _dataLoading.value = true

    viewModelScope.launch {
      repository.getTask(taskId).let { result ->
        if (result is Success) {
          onTaskLoaded(result.data)
        } else {
          onDataNotAvailable()
        }
      }
    }
  }

  private fun onTaskLoaded(task: Task) {
    title.value = task.title
    description.value = task.description
    _dataLoading.value = false
    taskCompleted = task.isCompleted
  }

  private fun onDataNotAvailable() {
    _dataLoading.value = false
  }

  fun saveTask() {
    val currentTitle = title.value
    val currentDesc = description.value

    if (currentTitle == null || currentDesc == null) {
      _snackbarText.value = Event("Please don't leave a field empty")
      return
    }

    val currentTaskId = taskId
    if (isNewTask || currentTaskId.isNullOrEmpty()) {
      createTask(Task(currentTitle, currentDesc))
    } else {
      val task = Task(currentTitle, currentDesc, taskCompleted, currentTaskId)
      updateTask(task)
    }
  }

  private fun createTask(task: Task) = viewModelScope.launch(dispatcher) {
    repository.saveTask(task)
    _taskUpdated.value = Event(Unit)
  }

  private fun updateTask(task: Task) = viewModelScope.launch(dispatcher) {
    repository.updateTask(task)
    _taskUpdated.value = Event(Unit)
  }
}
