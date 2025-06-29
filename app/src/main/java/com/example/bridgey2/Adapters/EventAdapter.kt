package com.example.bridgey2.Adapters

import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bridgey2.DetailActivity
import com.example.bridgey2.Models.Post
import com.example.bridgey2.R

class EventAdapter(private val events: ArrayList<Post>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

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
        holder.nameEvent.text = event.eventTitle
//        Glide.with(holder.imgEvent.context)
//            .load(event.eventImg)
//            .into(holder.imgEvent)
        val bytes = android.util.Base64.decode(event.eventImg,
            android.util.Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        holder.imgEvent.setImageBitmap(bitmap)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("POST_ID", event.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = events.size
}
