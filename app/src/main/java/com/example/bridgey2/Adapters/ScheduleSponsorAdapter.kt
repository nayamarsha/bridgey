package com.example.bridgey2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bridgey2.Models.ResponseSponsor
import com.example.bridgey2.R

class ScheduleSponsorAdapter(private val sponsors: List<ResponseSponsor>): RecyclerView.Adapter<ScheduleSponsorAdapter.SponsorViewHolder>() {
    class SponsorViewHolder(view: View): RecyclerView.ViewHolder(view){
        val logoSponsor: ImageView = view.findViewById(R.id.si_imageView)
        val header: TextView = view.findViewById(R.id.si_header)
        val subtitleSponsor: TextView = view.findViewById(R.id.item_scedule_subtitle)
        val descSponsor: TextView = view.findViewById(R.id.item_scedule_desc)
        val titleSponsor: TextView = view.findViewById(R.id.item_scedule_title)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SponsorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.schedule_item, parent, false)
        return SponsorViewHolder(view)
    }


    override fun onBindViewHolder(holder: SponsorViewHolder, position: Int) {
        val sponsor = sponsors[position]
        holder.header.text = sponsor.name ?: "No Header"
        holder.titleSponsor.text = sponsor.title ?: "No title"
        holder.descSponsor.text = sponsor.description?: "No Description"

        Glide.with(holder.logoSponsor.context)
            .load(sponsor.logo)
            .into(holder.logoSponsor)
    }

    override fun getItemCount(): Int = sponsors.size
}