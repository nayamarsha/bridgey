package com.example.bridgey2.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bridgey2.DetailSponsorActivity
import com.example.bridgey2.DetailTenantActvity
import com.example.bridgey2.Models.Sponsor
import com.example.bridgey2.Models.Tenant
import com.example.bridgey2.databinding.ScheduleItemBinding // Import binding class

class ScheduleTenantAdapter(private val tenants: ArrayList<Tenant>): RecyclerView.Adapter<ScheduleTenantAdapter.TenantViewHolder>() {

    inner class TenantViewHolder(val binding: ScheduleItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenantViewHolder {
        val binding = ScheduleItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TenantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TenantViewHolder, position: Int) {
        val tenant = tenants[position]
        holder.binding.apply {
            siHeader.text = tenant.name ?: "No Header"
            itemSceduleDesc.text = tenant.description?: "No Description"

            Glide.with(siImageView.context)
                .load(tenant.logo)
                .into(siImageView)

            btnDetail.setOnClickListener {
                val intent = Intent(root.context, DetailTenantActvity::class.java)
                intent.putExtra("TENANT_ID", tenant.id)
                root.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = tenants.size
}