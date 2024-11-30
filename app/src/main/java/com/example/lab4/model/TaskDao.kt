package com.example.lab4.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE roomId = :roomId")
    fun getTasksByRoom(roomId: Int): List<TaskEntity>

    @Query("SELECT * FROM tasks ORDER BY lastCompletedDate LIMIT 3")
    fun getOverdueTasks(): List<TaskEntity>

    @Insert
    fun insertTask(task: TaskEntity)
}