package com.example.bridgey2.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bridgey2.Adapters.ProposalAdapter
import com.example.bridgey2.Models.Proposal
import com.example.bridgey2.Models.ProposalStatus
import com.example.bridgey2.R

class StatusFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProposalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // ✅ Gunakan layout page-nya, yaitu list_proposal.xml
        val view = inflater.inflate(R.layout.list_proposal, container, false)

        recyclerView = view.findViewById(R.id.proposalRecyclerView)

        // ✅ Data dummy sementara
        val dummyProposals = listOf(
            Proposal(
                "1", "Tenant A", "https://via.placeholder.com/150", "10 Juni 2025", "On Review",
                listOf(
                    ProposalStatus("Proposal Uploaded", "Menunggu peninjauan", null, "10 Juni 2025 10:00"),
                    ProposalStatus("On Review", "Proposal sedang ditinjau", null, "11 Juni 2025 15:00", true)
                )
            ),
            Proposal(
                "2", "Tenant B", "https://via.placeholder.com/150", "9 Juni 2025", "Upload Agreement Document",
                listOf(
                    ProposalStatus("Proposal Uploaded", "Terkirim", null, "09 Juni 2025 09:00"),
                    ProposalStatus("On Review", "Sedang ditinjau", null, "10 Juni 2025 11:00"),
                    ProposalStatus("Accepted", "Proposal disetujui", null, "11 Juni 2025 13:00"),
                    ProposalStatus("Upload Agreement Document", "Menunggu unggah dokumen", null, "12 Juni 2025 16:00", true)
                )
            )
        )

        // ✅ Set adapter dan navigasi ke halaman detail jika "details" diklik
        adapter = ProposalAdapter(dummyProposals) { selectedProposal ->
            val bundle = Bundle().apply {
                putString("proposal", selectedProposal.id)
            }
            findNavController().navigate(R.id.itemStatusFragment, bundle)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        return view
    }
}
