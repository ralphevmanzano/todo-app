package com.example.todo_app.di

import android.content.Context
import androidx.room.Room
import com.example.todo_app.data.DefaultTasksRepository
import com.example.todo_app.data.TasksDataSource
import com.example.todo_app.data.TasksRepository
import com.example.todo_app.data.source.local.TaskDatabase
import com.example.todo_app.data.source.local.TasksLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.security.AccessControlContext
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module(includes = [ApplicationModuleBinds::class])
object ApplicationModule {

  @JvmStatic
  @Singleton
  @Provides
  fun provideTasksLocalDataSource(
    database: TaskDatabase,
    @Named("io") ioDispatcher: CoroutineDispatcher
  ): TasksDataSource {
    return TasksLocalDataSource(database.taskDao(), ioDispatcher)
  }

  @JvmStatic
  @Singleton
  @Provides
  fun provideDatabase(context: Context): TaskDatabase {
    return Room.databaseBuilder(
      context.applicationContext,
      TaskDatabase::class.java,
      "Tasks.db"
    ).build()
  }

  @JvmStatic
  @Singleton
  @Provides
  fun provideDefaultTaskRepository(tasksLocalDataSource: TasksDataSource, @Named("io") ioDispatcher: CoroutineDispatcher) : TasksRepository {
    return DefaultTasksRepository(tasksLocalDataSource, ioDispatcher)
  }

  @JvmStatic
  @Singleton
  @Provides
  @Named("io")
  fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

  @JvmStatic
  @Singleton
  @Provides
  @Named("main")
  fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}

@Module
abstract class ApplicationModuleBinds {

//  @Singleton
//  @Binds
//  abstract fun bindRepository(repository: DefaultTasksRepository): TasksRepository
}