package com.example.bridgey2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bridgey2.FeedItem
import com.example.bridgey2.R

class FeedAdapter(private var items: List<FeedItem>) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    fun updateData(newItems: List<FeedItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false)
        return FeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivFeedImage: ImageView = itemView.findViewById(R.id.ivFeedImage)

        fun bind(feedItem: FeedItem) {
            Glide.with(itemView.context)
                .load(feedItem.imageUrl)
                .into(ivFeedImage)
        }
    }
}

