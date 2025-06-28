package com.example.bridgey2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bridgey2.Models.ResponseSponsor
import com.example.bridgey2.Models.Sponsor
import com.example.bridgey2.Models.Tenant
import com.example.bridgey2.R

class TenantAdapter(private val tenants: ArrayList<Tenant>) :
    RecyclerView.Adapter<TenantAdapter.TenantViewHolder>() {

    class TenantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgTenant: ImageView = view.findViewById(R.id.imgTenant)
        val nameTenant: TextView = view.findViewById(R.id.nameTenant)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tenant_item, parent, false)
        return TenantViewHolder(view)
    }

    override fun onBindViewHolder(holder: TenantViewHolder, position: Int) {
        val tenant = tenants[position]
        holder.nameTenant.text = tenant.name

        Glide.with(holder.imgTenant.context)
            .load(tenant.logo)
            .into(holder.imgTenant)
    }

    override fun getItemCount(): Int = tenants.size
}