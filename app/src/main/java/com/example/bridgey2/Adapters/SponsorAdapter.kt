package com.example.bridgey2.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bridgey2.DetailSponsorActivity
import com.example.bridgey2.Models.ResponseSponsor
import com.example.bridgey2.Models.Sponsor
import com.example.bridgey2.R

class SponsorAdapter(private val sponsors: ArrayList<Sponsor>) :
    RecyclerView.Adapter<SponsorAdapter.SponsorViewHolder>() {

    class SponsorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgSponsor: ImageView = view.findViewById(R.id.imgSponsor)
        val nameSponsor: TextView = view.findViewById(R.id.nameSponsor)
        val titleSponsor: TextView = view.findViewById(R.id.titleSponsor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SponsorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sponsor_item, parent, false)
        return SponsorViewHolder(view)
    }

    override fun onBindViewHolder(holder: SponsorViewHolder, position: Int) {
        val sponsor = sponsors[position]
        holder.nameSponsor.text = sponsor.name
//        holder.titleSponsor.text = sponsor.description

        Glide.with(holder.imgSponsor.context)
            .load(sponsor.logo)
            .into(holder.imgSponsor)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailSponsorActivity::class.java)
            intent.putExtra("SPONSOR_ID", sponsor.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = sponsors.size
}