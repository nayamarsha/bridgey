package com.example.bridgey.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bridgey2.R
import com.example.bridgey2.Models.SearchResult

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var searchResults: List<SearchResult> = listOf()

    fun submitList(results: List<SearchResult>) {
        searchResults = results
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.itemTitle)
        val dateText: TextView = view.findViewById(R.id.itemDate)
        val locationText: TextView = view.findViewById(R.id.itemLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_result, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = searchResults.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = searchResults[position]
        holder.titleText.text = item.name
        holder.dateText.text = item.description
        holder.locationText.text = item.location
    }
}
