package com.example.bridgey2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bridgey2.Models.ResponseEvent
import com.example.bridgey2.R

class EventAdapter(private val events: List<ResponseEvent>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgEvent: ImageView = view.findViewById(R.id.imgEvent)
        val nameEvent: TextView = view.findViewById(R.id.nameEvent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.nameEvent.text = event.name
        Glide.with(holder.imgEvent.context)
            .load(event.imageUrl)
            .into(holder.imgEvent)
    }

    override fun getItemCount(): Int = events.size
}
