package com.example.bridgey2.Adapters

import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bridgey2.DetailActivity
import com.example.bridgey2.Models.Post
import com.example.bridgey2.databinding.ScheduleItemBinding

class ScheduleEventAdapter(private val events: ArrayList<Post>) :
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
            itemSceduleTitle.text = event.eventTitle ?: "No Title"
            itemSceduleSubtitle.text = event.eventLocation ?: "No Location"
            itemSceduleDesc.text = event.eventDesc ?: "No Description"

            // Tampilkan gambar dari base64 (langsung di list)
            val bytes = android.util.Base64.decode(event.eventImg, android.util.Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            siImageView.setImageBitmap(bitmap)

            // Saat klik tombol detail, kirim hanya ID ke DetailActivity
            btnDetail.setOnClickListener {
                val intent = Intent(root.context, DetailActivity::class.java)
                intent.putExtra("POST_ID", event.id) // hanya ID
                root.context.startActivity(intent)
            }
        }
    }



    override fun getItemCount(): Int = events.size
}
