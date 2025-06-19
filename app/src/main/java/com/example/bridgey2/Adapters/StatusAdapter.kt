package com.example.bridgey2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bridgey2.Models.ProposalStatus
import com.example.bridgey2.R

class StatusAdapter(private val statusList: List<ProposalStatus>) :
    RecyclerView.Adapter<StatusAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: View = itemView.findViewById(R.id.statusCard)
        val title: TextView = itemView.findViewById(R.id.tvJudulStatus)
        val description: TextView = itemView.findViewById(R.id.tvDeskripsiStatus)
        val dateTime: TextView = itemView.findViewById(R.id.tvTanggalStatus)
        val file: TextView = itemView.findViewById(R.id.tvNamaFile)
        val circle: View = itemView.findViewById(R.id.circleView)
        val garisTimeline: View = itemView.findViewById(R.id.garisTimeline)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_status, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val status = statusList[position]
        holder.title.text = status.title
        holder.description.text = status.description
        holder.dateTime.text = status.dateTime
        holder.file.text = status.fileName ?: "-"
        holder.circle.setBackgroundResource(
            if (status.isLatest) R.drawable.status_active else R.drawable.status_circle
        )
        holder.card.setBackgroundResource(
            if (status.isLatest) R.drawable.status_card_active else R.drawable.status_card_bg
        )

        // 🔄 Tampilkan garis timeline hanya jika bukan item terakhir
        if (position < statusList.size - 1) {
            holder.garisTimeline.visibility = View.VISIBLE

            // ✅ Algoritma: Sesuaikan tinggi garis dengan tinggi card setelah layout selesai
            holder.card.post {
                val cardHeight = holder.card.height
                val params = holder.garisTimeline.layoutParams
                params.height = cardHeight
                holder.garisTimeline.layoutParams = params
            }
        } else {
            holder.garisTimeline.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = statusList.size
}