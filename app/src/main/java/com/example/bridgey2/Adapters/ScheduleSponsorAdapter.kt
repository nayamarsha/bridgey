package com.example.bridgey2.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bridgey2.DetailSponsorActivity
import com.example.bridgey2.Models.Sponsor
import com.example.bridgey2.databinding.ScheduleItemBinding // Import binding class

class ScheduleSponsorAdapter(private val sponsors: ArrayList<Sponsor>): RecyclerView.Adapter<ScheduleSponsorAdapter.SponsorViewHolder>() {

    inner class SponsorViewHolder(val binding: ScheduleItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SponsorViewHolder {
        val binding = ScheduleItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SponsorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SponsorViewHolder, position: Int) {
        val sponsor = sponsors[position]
        holder.binding.apply {
            siHeader.text = sponsor.name ?: "No Header"
//            itemSceduleTitle.text = sponsor. ?: "No title"
            itemSceduleDesc.text = sponsor.description?: "No Description"

            Glide.with(siImageView.context)
                .load(sponsor.logo)
                .into(siImageView)

            btnDetail.setOnClickListener {
                val intent = Intent(root.context, DetailSponsorActivity::class.java)
                intent.putExtra("SPONSOR_ID", sponsor.id)
                root.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = sponsors.size
}