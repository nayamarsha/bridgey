package com.example.bridgey2.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bridgey2.Adapters.StatusAdapter
import com.example.bridgey2.Models.Proposal
import com.example.bridgey2.R
import com.example.bridgey2.databinding.TimelineDetailBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ItemStatusFragment : Fragment() {

    private var _binding: TimelineDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: StatusAdapter

    private val args: ItemStatusFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TimelineDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedProposal: Proposal = args.selectedProposal

        binding.proposalTitle.text = "Timeline Proposal: ${selectedProposal.tenantName}"
        adapter = StatusAdapter(selectedProposal.statusList)
        binding.statusRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.statusRecyclerView.adapter = adapter

        // Tombol kembali manual ke StatusFragment
        binding.setBack.setOnClickListener {
            // Navigasi balik secara eksplisit dan bersih
            findNavController().navigate(
                R.id.statusFragment,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.statusFragment, true)
                    .setLaunchSingleTop(true)
                    .build()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
