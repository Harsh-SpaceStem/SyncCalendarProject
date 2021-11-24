package com.example.testproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class EventAdapter(
    private val context: Context,
    private val taskList: ArrayList<Events>,
) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {


    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.findViewById(R.id.tv_event_id)
        val title: TextView = itemView.findViewById(R.id.tv_event_title)
        val allDay: SwitchCompat = itemView.findViewById(R.id.tv_event_allDay)
        val startTime: TextView = itemView.findViewById(R.id.tv_event_startTime)
        val endTime: TextView = itemView.findViewById(R.id.tv_event_endTime)
        val description: TextView = itemView.findViewById(R.id.tv_event_description)
        val location: TextView = itemView.findViewById(R.id.tv_event_location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.task_item, parent, false)

        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.id.text = taskList[position].id
        holder.title.text = taskList[position].title
        holder.description.text = taskList[position].description
        if (taskList[position].location.isEmpty()) {
            holder.location.text = "No Location Given to this Event"
        } else {
            holder.location.text = taskList[position].location
        }
        holder.allDay.isChecked = taskList[position].allDay == "1"
        holder.startTime.text =
            getDate(taskList[position].dateStart.toLong())
        holder.endTime.text = getDate(taskList[position].dateEnd.toLong())
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    private fun getDate(milliSeconds: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.getDefault())

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

}