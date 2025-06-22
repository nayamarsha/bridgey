package com.example.bridgey2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bridgey2.R
import com.example.bridgey2.Models.SearchResult

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private val data = ArrayList<SearchResult>()

    fun submitList(list: List<SearchResult>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.itemTitle)
        val date: TextView = view.findViewById(R.id.itemDate)
        val location: TextView = view.findViewById(R.id.itemLocation)
        val image: ImageView = view.findViewById(R.id.photos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_result, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.title.text = item.name
        holder.date.text = item.date
        holder.location.text = item.location

        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .placeholder(R.drawable.temp_poster)
            .into(holder.image)
    }
}
