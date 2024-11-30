package com.example.lab4

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.lab4.model.AppDatabase
import com.example.lab4.model.TaskEntity

class AddTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "home-db"
        ).allowMainThreadQueries().build()

        val taskNameInput = findViewById<EditText>(R.id.etTaskName)
        val taskDurationInput = findViewById<EditText>(R.id.etTaskTime).toString().toIntOrNull() ?: 0
        val periodicityInput = findViewById<EditText>(R.id.etTaskPeriodicity).toString().toIntOrNull() ?: 0
        val roomId = intent.getIntExtra("roomId", -1) // Передаётся при вызове
        val addButton = findViewById<Button>(R.id.btnAddTask)

        addButton.setOnClickListener {
            val task = TaskEntity(
                roomId = roomId,
                name = taskNameInput.text.toString(),
                time = taskDurationInput,
                periodicity = periodicityInput,
                lastCompletedDate = System.currentTimeMillis()
            )

            db.roomDao().insertTask(task)
            finish()
        }
    }
}
