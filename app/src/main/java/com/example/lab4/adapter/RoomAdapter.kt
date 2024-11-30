package com.example.lab4.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab4.R
import com.example.lab4.model.RoomEntity

class RoomAdapter : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    private var rooms: List<RoomEntity> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = rooms[position]
        holder.bind(room)
    }

    override fun getItemCount(): Int = rooms.size

    // Метод для обновления данных в адаптере
    fun submitList(newRooms: List<RoomEntity>) {
        rooms = newRooms
        notifyDataSetChanged()
    }

    inner class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val roomName: TextView = itemView.findViewById(R.id.tvRoomName)

        fun bind(room: RoomEntity) {
            roomName.text = room.name
        }
    }
}
