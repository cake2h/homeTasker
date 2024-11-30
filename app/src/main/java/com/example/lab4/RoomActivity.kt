package com.example.lab4

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.lab4.adapter.TaskAdapter
import com.example.lab4.model.AppDatabase

class RoomActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        // Получаем ID комнаты из Intent
        val roomId = intent.getIntExtra("roomId", -1)
        if (roomId == -1) {
            finish() // Если ID комнаты некорректный, завершаем Activity
            return
        }

        // Инициализация базы данных
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-database"
        ).allowMainThreadQueries().build()

        // Получаем данные о комнате из базы
        val room = db.roomDao().getRoomById(roomId)
        if (room == null) {
            finish() // Если комнаты с таким ID нет, завершаем Activity
            return
        }

        // Устанавливаем имя комнаты
        findViewById<TextView>(R.id.tvRoomName).text = room.name

        // Обработка кнопки "Назад"
        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            finish() // Возврат на главный экран
        }

        // Обработка кнопки "Добавить задачу"
        findViewById<ImageButton>(R.id.btnAddTask).setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra("roomId", roomId)
            startActivity(intent)
        }

        // Настраиваем RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
//        taskAdapter = TaskAdapter { task ->
//            // Можно добавить обработку нажатия на задачу
//        }
        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Загружаем задачи для комнаты
        val tasks = db.taskDao().getTasksByRoom(roomId)
        val allRooms = db.roomDao().getAllRooms() // Подгружаем комнаты для адаптера
        taskAdapter.submitData(tasks, allRooms)
    }
}
