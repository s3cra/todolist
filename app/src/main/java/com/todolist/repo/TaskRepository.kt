package com.todolist.repo

import com.todolist.dao.TaskDao
import com.todolist.entity.Task

class TaskRepository(private val taskDao: TaskDao) {
    val allTasks = taskDao.getAllTasks()
    suspend fun insert(task: Task) = taskDao.insertTask(task)
    suspend fun update(task: Task) = taskDao.updateTask(task)
    suspend fun delete(task: Task) = taskDao.deleteTask(task)
}
