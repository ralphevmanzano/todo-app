package com.example.todo_app.ui.addtask

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.todo_app.LiveDataTestUtil
import com.example.todo_app.TestMainCoroutineRule
import com.example.todo_app.data.TasksRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyBlocking
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AddTaskViewModelTest {

  private lateinit var addTaskViewModel: AddTaskViewModel
  private lateinit var tasksRepository: TasksRepository

  @ExperimentalCoroutinesApi
  @get:Rule
  var mainCoroutineRule = TestMainCoroutineRule()

  // Executes each task synchronously using Architecture Components.
  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @Before
  fun setupViewModel() {
    tasksRepository = mock{}
    addTaskViewModel = AddTaskViewModel(tasksRepository, mainCoroutineRule.testDispatcher)
  }

  @Test
  fun inputTitleDescription_clickSave() {

    addTaskViewModel.apply {
      title.value = "title"
      description.value = "description"
    }

    addTaskViewModel.saveTask()

    verifyBlocking(tasksRepository) {saveTask(any())}
  }

  @Test
  fun emptyInputs_clickSave() {
    addTaskViewModel.apply {
      title.value = null
      description.value = null
    }

    addTaskViewModel.saveTask()

    verifyZeroInteractions(tasksRepository)

    val value = LiveDataTestUtil.getValue(addTaskViewModel.snackbarText)
    assertThat(value.getContentIfNotHandled(), CoreMatchers.notNullValue())
  }

  @Test
  fun emptyTitle_clickSave_showSnackbar() {
    addTaskViewModel.apply {
      title.value = null
      description.value = "description"
    }

    addTaskViewModel.saveTask()

    verifyZeroInteractions(tasksRepository)

    val value = LiveDataTestUtil.getValue(addTaskViewModel.snackbarText)
    assertThat(value.getContentIfNotHandled(), CoreMatchers.notNullValue())
  }

  @Test
  fun emptyDescription_clickSave_showSnackbar() {
    addTaskViewModel.apply {
      title.value = "title"
      description.value = null
    }

    addTaskViewModel.saveTask()

    verifyZeroInteractions(tasksRepository)

    val value = LiveDataTestUtil.getValue(addTaskViewModel.snackbarText)
    assertThat(value.getContentIfNotHandled(), CoreMatchers.notNullValue())
  }

}