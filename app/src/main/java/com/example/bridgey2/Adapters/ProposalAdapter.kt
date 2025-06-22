// com/example/bridgey2/Adapters/ProposalAdapter.kt
package com.example.bridgey2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bridgey2.Models.Proposal
import com.example.bridgey2.R

class ProposalAdapter(
    private val proposalList: List<Proposal>,
    private val onDetailClick: (Proposal) -> Unit
) : RecyclerView.Adapter<ProposalAdapter.ProposalViewHolder>() {

    inner class ProposalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logoImage: ImageView = itemView.findViewById(R.id.logoImage)
        val tenantName: TextView = itemView.findViewById(R.id.tenantName)
        val submissionDate: TextView = itemView.findViewById(R.id.submissionDate)
        val currentStatus: TextView = itemView.findViewById(R.id.currentStatus)
        val detailText: TextView = itemView.findViewById(R.id.detailstatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProposalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_status, parent, false)
        return ProposalViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProposalViewHolder, position: Int) {
        val proposal = proposalList[position]
        holder.tenantName.text = proposal.tenantName
        holder.submissionDate.text = "Diajukan pada ${proposal.submissionDate}"
        holder.currentStatus.text = "Status: ${proposal.currentStatus}"

        // Load logo dari URL menggunakan Glide
        Glide.with(holder.itemView.context)
            .load(proposal.logoUrl)
            .into(holder.logoImage)

        // Klik pada teks "details"
        holder.detailText.setOnClickListener {
            onDetailClick(proposal)
        }
    }

    override fun getItemCount(): Int = proposalList.size
}
