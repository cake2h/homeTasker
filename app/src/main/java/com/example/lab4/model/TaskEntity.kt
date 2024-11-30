package com.example.lab4.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val roomId: Int, // Связь с помещением
    val name: String,
    val time: Int, // Время выполнения
    val periodicity: Int, // Периодичность
    val lastCompletedDate: Long // Последняя дата выполнения в формате timestamp
)
