package com.todolist.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.todolist.entity.Category

@Dao
interface CategoryDao {
    @Insert
    suspend fun insertCategory(category: Category)
    @Query("SELECT * FROM categories")
    fun getAllCategories(): LiveData<List<Category>>
}