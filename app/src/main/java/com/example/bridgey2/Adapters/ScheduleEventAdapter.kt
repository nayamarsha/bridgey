package com.example.bridgey2.Adapters

import android.content.Intent
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bridgey2.DetailActivity
import com.example.bridgey2.Models.ResponseEvent
import com.example.bridgey2.databinding.ScheduleItemBinding

class ScheduleEventAdapter(private val events: List<ResponseEvent>) :
    RecyclerView.Adapter<ScheduleEventAdapter.EventViewHolder>() {

    inner class EventViewHolder(val binding: ScheduleItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ScheduleItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.binding.apply {
            itemSceduleTitle.text = event.name ?: "No Title"
            itemSceduleSubtitle.text = event.location ?: "No Location"
            itemSceduleDesc.text = event.description ?: "No Description"

            Glide.with(siImageView.context)
                .load(event.imageUrl)
                .into(siImageView)

            // Tambahkan klik listener ke root view (atau tombol tertentu)
            holder.binding.btnDetail.setOnClickListener {
                val intent = Intent(root.context, DetailActivity::class.java)
                intent.putExtra("EVENT_DATA", event)
                root.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = events.size
}