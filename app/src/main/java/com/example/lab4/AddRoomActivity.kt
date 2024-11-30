package com.example.lab4

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.lab4.adapter.RoomAdapter
import com.example.lab4.model.AppDatabase
import com.example.lab4.model.RoomEntity

class AddRoomActivity : AppCompatActivity() {

    private lateinit var roomAdapter: RoomAdapter

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_room)

        val roomTypes = arrayOf("Кухня", "Ванная", "Гостиная", "Спальня")

        // Находим Spinner по ID
        val spinner = findViewById<Spinner>(R.id.spRoomType)

        // Создаем адаптер для Spinner с использованием массива
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,  // Стандартный макет для элементов
            roomTypes  // Массив данных для Spinner
        )

        // Привязываем адаптер к Spinner
        spinner.adapter = adapter

        // Обработчик выбора элемента
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Получаем выбранный элемент
                val selectedRoomType = parentView.getItemAtPosition(position).toString()
                // Выводим выбранный тип комнаты, например, в Toast
                Toast.makeText(applicationContext, "Выбран тип комнаты: $selectedRoomType", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Можно оставить пустым, если нужно
            }
        }

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app-database"
        ).allowMainThreadQueries().build()

        val roomNameInput = findViewById<EditText>(R.id.etRoomName)
        val roomTypeSpinner = findViewById<Spinner>(R.id.spRoomType)
        val addButton = findViewById<Button>(R.id.btnAddRoom)

        addButton.setOnClickListener {
            val name = roomNameInput.text.toString()
            val type = roomTypeSpinner.selectedItem.toString()

            val room = RoomEntity(name = name, type = type)
            db.roomDao().insertRoom(room)

            val rooms = db.roomDao().getAllRooms()
            roomAdapter.submitList(rooms)

            finish() // Возврат на главный экран
        }
    }
}
