package com.example.bridgey2.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView // Tetap perlu ini jika menggunakan findViewById
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bridgey2.Adapters.StatusAdapter
import com.example.bridgey2.Models.Proposal // Pastikan ini diimpor
import com.example.bridgey2.R
import androidx.navigation.fragment.navArgs // Import ini untuk Safe Args
import com.example.bridgey2.databinding.TimelineDetailBinding // Asumsi nama binding untuk timeline_detail.xml

class ItemStatusFragment : Fragment() {

    // Jika kamu menggunakan View Binding untuk timeline_detail.xml
    private var _binding: TimelineDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: StatusAdapter

    // Menggunakan Safe Args untuk mengambil argumen
    private val args: ItemStatusFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout menggunakan View Binding (direkomendasikan)
        _binding = TimelineDetailBinding.inflate(inflater, container, false)
        return binding.root

        // Jika kamu masih ingin menggunakan findViewById secara manual:
        // val view = inflater.inflate(R.layout.timeline_detail, container, false)
        // return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil objek Proposal yang dikirimkan melalui Safe Args
        // Nama "selectedProposal" harus cocok dengan nama <argument> di nav_graph.xml
        val selectedProposal: Proposal = args.selectedProposal

        // Sekarang gunakan objek selectedProposal untuk mengisi UI
        // Jika menggunakan View Binding:
        binding.proposalTitle.text = "Timeline Proposal: ${selectedProposal.tenantName}"
        adapter = StatusAdapter(selectedProposal.statusList) // Pastikan nama variabel status history di Proposal
        binding.statusRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.statusRecyclerView.adapter = adapter


        // Jika kamu masih menggunakan findViewById:
        /*
        val recyclerView: RecyclerView = view.findViewById(R.id.statusRecyclerView)
        val titleView: TextView = view.findViewById(R.id.proposalTitle)

        titleView.text = "Timeline Proposal: ${selectedProposal.tenantName}"

        adapter = StatusAdapter(selectedProposal.statusHistory) // Pastikan nama variabel status history di Proposal
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        */
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Penting untuk membersihkan referensi binding
        _binding = null
    }
}