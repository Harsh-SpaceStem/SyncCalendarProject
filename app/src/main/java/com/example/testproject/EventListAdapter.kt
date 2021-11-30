package com.example.testproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.testproject.event_list.EventListItem

class EventListAdapter(
    private val context: Context,
    private val taskList: List<EventListItem>,
) :
    RecyclerView.Adapter<EventListAdapter.EventViewHolder>() {


    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.findViewById(R.id.tv_event_id)
        val title: TextView = itemView.findViewById(R.id.tv_event_title)
        val allDay: SwitchCompat = itemView.findViewById(R.id.tv_event_allDay)
        val startTime: TextView = itemView.findViewById(R.id.tv_event_startTime)
        val endTime: TextView = itemView.findViewById(R.id.tv_event_endTime)
        val description: TextView = itemView.findViewById(R.id.tv_event_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.task_item, parent, false)

        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.id.text = taskList[position].id
        holder.title.text = taskList[position].summary
        holder.description.text = taskList[position].description
        holder.allDay.isChecked = taskList[position].reminders.useDefault == true
        holder.startTime.text = taskList[position].start.dateTime
        holder.endTime.text = taskList[position].end.dateTime
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

}