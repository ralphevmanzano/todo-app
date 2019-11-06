package com.example.todo_app.di

import android.content.Context
import com.example.todo_app.TodoApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    ApplicationModule::class,
    AndroidSupportInjectionModule::class,
    TasksModule::class,
    AddTaskModule::class
  ]
)
interface ApplicationComponent : AndroidInjector<TodoApp> {

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance appContext: Context): ApplicationComponent
  }
}