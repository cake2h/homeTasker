package com.example.lab4.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RoomDao {
    @Query("SELECT * FROM rooms")
    fun getAllRooms(): List<RoomEntity>

    @Insert
    fun insertRoom(room: RoomEntity)

    @Query("SELECT * FROM tasks WHERE roomId = :roomId")
    fun getTasksByRoomId(roomId: Int): List<TaskEntity>

    @Query("SELECT * FROM rooms WHERE id = :roomId LIMIT 1")
    fun getRoomById(roomId: Int): RoomEntity?

    @Insert
    fun insertTask(task: TaskEntity)

    @Query("SELECT * FROM tasks ORDER BY lastCompletedDate ASC LIMIT 3")
    fun getOverdueTasks(): List<TaskEntity>
}
