package com.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.todolist.entity.Task
import com.todolist.repo.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repo: TaskRepository
    val allTasks: LiveData<List<Task>>

    init {
        val db = AppDatabase.getInstance(application)
        repo = TaskRepository(db.taskDao())
        allTasks = repo.allTasks
    }

    fun insert(task: Task) = viewModelScope.launch { repo.insert(task) }
    fun update(task: Task) = viewModelScope.launch { repo.update(task) }
    fun delete(task: Task) = viewModelScope.launch { repo.delete(task) }
}