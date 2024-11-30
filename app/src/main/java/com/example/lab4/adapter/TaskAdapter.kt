package com.example.lab4.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab4.R
import com.example.lab4.model.RoomEntity
import com.example.lab4.model.TaskEntity
import java.util.concurrent.TimeUnit

class TaskAdapter(
    private val onClick: (TaskEntity) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val tasks = mutableListOf<TaskEntity>()
    private val rooms = mutableListOf<RoomEntity>()

    /**
     * Метод для обновления данных задач и комнат.
     */
    fun submitData(newTasks: List<TaskEntity>, newRooms: List<RoomEntity>) {
        tasks.clear()
        rooms.clear()
        tasks.addAll(newTasks)
        rooms.addAll(newRooms)
        notifyDataSetChanged()
    }

    fun submitList(newTasks: List<TaskEntity>) {
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged() // Уведомляем адаптер о том, что данные обновились
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task, rooms.find { it.id == task.roomId }?.name ?: "Неизвестная комната", onClick)
    }

    override fun getItemCount() = tasks.size

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTaskName: TextView = itemView.findViewById(R.id.tvTaskName)
        private val tvTaskRoom: TextView = itemView.findViewById(R.id.tvTaskRoom)
        private val tvTaskTime: TextView = itemView.findViewById(R.id.tvTaskTime)

        fun bind(task: TaskEntity, roomName: String, onClick: (TaskEntity) -> Unit) {
            tvTaskName.text = task.name
            tvTaskRoom.text = roomName
            tvTaskTime.text = "${task.time} минут"

            // Определяем цвет задачи в зависимости от давности выполнения
            val currentTime = System.currentTimeMillis()
            val daysSinceLastDone = TimeUnit.MILLISECONDS.toDays(currentTime - task.lastCompletedDate)
            val backgroundColor = when {
                daysSinceLastDone <= task.periodicity / 2 -> Color.parseColor("#DFF0D8") // Зеленый
                daysSinceLastDone <= task.periodicity -> Color.parseColor("#FCF8E3") // Желтый
                else -> Color.parseColor("#F2DEDE") // Красный
            }
            itemView.setBackgroundColor(backgroundColor)

            // Обработка нажатия
            itemView.setOnClickListener { onClick(task) }
        }
    }
}
