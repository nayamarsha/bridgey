package com.example.bridgey2.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bridgey2.DetailActivity
import com.example.bridgey2.DetailSponsorActivity
import com.example.bridgey2.DetailTenantActvity
import com.example.bridgey2.R
import com.example.bridgey2.Models.SearchItem

class SearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<SearchItem> = listOf()

    fun submitList(newItems: List<SearchItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    companion object {
        private const val TYPE_EVENT = 0
        private const val TYPE_SPONSOR = 1
        private const val TYPE_TENANT = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position].type) {
            "Event" -> TYPE_EVENT
            "Sponsor" -> TYPE_SPONSOR
            "Tenant" -> TYPE_TENANT
            else -> TYPE_EVENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_EVENT -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_event, parent, false)
                EventViewHolder(view)
            }
            TYPE_SPONSOR -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_sponsor, parent, false)
                SponsorViewHolder(view)
            }
            TYPE_TENANT -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_tenant, parent, false)
                TenantViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_event, parent, false)
                EventViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        when (holder) {
            is EventViewHolder -> {
                val item = items[position]
                holder.title.text = item.name
                holder.date.text = item.date
                holder.location.text = item.location

                Glide.with(holder.itemView.context)
                    .load(item.imageUrl)
                    .placeholder(R.drawable.temp_poster)
                    .into(holder.image)

                holder.itemView.setOnClickListener {
                    val context = holder.itemView.context
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("POST_ID", item.id)
                    context.startActivity(intent)
                }
            }

            is SponsorViewHolder -> {
                val item = items[position]
                holder.title.text = item.name

                Glide.with(holder.itemView.context)
                    .load(item.logo)
                    .placeholder(R.drawable.temp_poster)
                    .into(holder.logo)

                holder.itemView.setOnClickListener {
                    val context = holder.itemView.context
                    val intent = Intent(context, DetailSponsorActivity::class.java)
                    intent.putExtra("SPONSOR_ID", item.id)
                    context.startActivity(intent)
                }
            }

            is TenantViewHolder -> {
                holder.title.text = item.name
                Glide.with(holder.itemView.context)
                    .load(item.logo)
                    .placeholder(R.drawable.temp_poster)
                    .into(holder.logo)

                holder.itemView.setOnClickListener {
                    val context = holder.itemView.context
                    val intent = Intent(context, DetailTenantActvity::class.java)
                    intent.putExtra("TENANT_ID", item.id)
                    context.startActivity(intent)
                }
            }
        }
    }

    inner class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.itemTitle)
        val date: TextView = view.findViewById(R.id.itemDate)
        val location: TextView = view.findViewById(R.id.itemLocation)
        val image: ImageView = view.findViewById(R.id.photos)
    }

    inner class SponsorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.itemTitle)
        val logo: ImageView = view.findViewById(R.id.photos)
    }

    inner class TenantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.itemTitle)
        val logo: ImageView = view.findViewById(R.id.photos)
    }
}
