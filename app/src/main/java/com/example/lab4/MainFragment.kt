package com.example.lab4

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.lab4.adapter.RoomAdapter
import com.example.lab4.adapter.TaskAdapter
import com.example.lab4.model.AppDatabase
import com.example.lab4.model.TaskEntity

class MainFragment : Fragment() {

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var roomAdapter: RoomAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Создаем вьюшку
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        // Теперь ищем кнопку после того, как вьюшка была создана
        val buttonAdd = view.findViewById<Button>(R.id.buttonAdd)
        buttonAdd.setOnClickListener {
            // Открытие активити для добавления комнаты
            val intent = Intent(requireContext(), AddRoomActivity::class.java)
            startActivity(intent)
        }

        // Инициализируем базу данных
        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "app-database"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

        // Настроить RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        taskAdapter = TaskAdapter { task: TaskEntity ->
            val intent = Intent(requireContext(), RoomActivity::class.java)
            intent.putExtra("roomId", task.roomId)
            startActivity(intent)
        }
        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Получаем 3 задачи, которые выполнялись давно
        val recyclerViewRooms = view.findViewById<RecyclerView>(R.id.rvRooms)
        roomAdapter = RoomAdapter()
        recyclerViewRooms.adapter = roomAdapter
        recyclerViewRooms.layoutManager = LinearLayoutManager(requireContext())

        // Получаем все комнаты из базы данных
        val rooms = db.roomDao().getAllRooms()

        // Обновляем адаптер
        roomAdapter.submitList(rooms)

        return view


    }
}

