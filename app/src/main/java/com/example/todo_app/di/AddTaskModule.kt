package com.example.todo_app.di

import androidx.lifecycle.ViewModel
import com.example.todo_app.ui.addtask.AddTaskFragment
import com.example.todo_app.ui.addtask.AddTaskViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class AddTaskModule {

  @ContributesAndroidInjector(modules = [
    ViewModelBuilder::class
  ])
  internal abstract fun addTaskFragment(): AddTaskFragment

  @Binds
  @IntoMap
  @ViewModelKey(AddTaskViewModel::class)
  abstract fun bindViewModel(viewModel: AddTaskViewModel): ViewModel
}