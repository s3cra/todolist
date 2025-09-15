package com.todolist.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.todolist.entity.Task

@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(task: Task)
    @Update
    suspend fun updateTask(task: Task)
    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks ORDER BY dueDateTime ASC")
    fun getAllTasks(): LiveData<List<Task>>
}