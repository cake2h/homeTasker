package com.example.lab4.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomEntity::class, TaskEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun roomDao(): RoomDao
}