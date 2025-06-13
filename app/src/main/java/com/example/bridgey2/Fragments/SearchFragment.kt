package com.example.bridgey2.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bridgey2.R
import com.example.bridgey2.Models.SearchResult
import com.example.bridgey2.Api.ApiConfig
import com.example.bridgey2.Adapters.SearchAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private lateinit var searchInput: EditText
    private lateinit var searchButton: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter

    private lateinit var btnAll: Button
    private lateinit var btnEvent: Button
    private lateinit var btnSponsor: Button
    private lateinit var btnTenant: Button

    private var selectedCategory = "All"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchInput = view.findViewById(R.id.searchInput)
        searchButton = view.findViewById(R.id.searchButton)
        recyclerView = view.findViewById(R.id.searchRecyclerView)

        btnAll = view.findViewById(R.id.btnAll)
        btnEvent = view.findViewById(R.id.btnEvent)
        btnSponsor = view.findViewById(R.id.btnSponsor)
        btnTenant = view.findViewById(R.id.btnTenant)

        adapter = SearchAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        setupCategoryButtons()

        searchButton.setOnClickListener {
            performSearch()
        }
    }

    private fun setupCategoryButtons() {
        val multiSelectButtons = listOf(btnEvent, btnSponsor, btnTenant)

        fun updateAllButtonState() {
            val allInactive = multiSelectButtons.none { it.isSelected }
            val allActive = multiSelectButtons.all { it.isSelected }

            if (allActive) {
                // Semua aktif, auto pindah ke ALL
                multiSelectButtons.forEach { it.isSelected = false }
                btnAll.isSelected = true
                selectedCategory = btnAll.text.toString()
                performSearch()
            } else if (btnAll.isSelected && !allInactive) {
                // Jika All aktif dan mulai pilih kategori lain, nonaktifkan All
                btnAll.isSelected = false
            }

            if (allInactive) {
                // Kalau semua mati, aktifkan kembali All
                btnAll.isSelected = true
                selectedCategory = btnAll.text.toString()
                performSearch()
            }
        }

        // ALL hanya satu
        btnAll.setOnClickListener {
            btnAll.isSelected = true
            multiSelectButtons.forEach { it.isSelected = false }
            selectedCategory = btnAll.text.toString()
            performSearch()
        }

        // Event / Sponsor / Tenant bisa multiple
        multiSelectButtons.forEach { button ->
            button.setOnClickListener {
                button.isSelected = !button.isSelected
                updateAllButtonState()

                if (!btnAll.isSelected) {
                    val selected = multiSelectButtons
                        .filter { it.isSelected }
                        .joinToString(", ") { it.text.toString() }
                    selectedCategory = selected
                    performSearch()
                }
            }
        }

        // Default state: All aktif
        btnAll.isSelected = true
        multiSelectButtons.forEach { it.isSelected = false }
    }

    private fun performSearch() {
        val query = searchInput.text.toString().trim()
        val service = ApiConfig.getService()

        val allList = mutableListOf<SearchResult>()

        // Fungsi ambil data lalu filter lokal
        fun fetchAndFilter(call: Call<List<SearchResult>>, onComplete: () -> Unit) {
            call.enqueue(object : Callback<List<SearchResult>> {
                override fun onResponse(call: Call<List<SearchResult>>, response: Response<List<SearchResult>>) {
                    if (response.isSuccessful) {
                        val filtered = response.body()?.filter {
                            it.name?.contains(query, ignoreCase = true) == true
                        } ?: emptyList()
                        allList.addAll(filtered)
                    }
                    onComplete()
                }

                override fun onFailure(call: Call<List<SearchResult>>, t: Throwable) {
                    t.printStackTrace()
                    onComplete()
                }
            })
        }

        val calls = mutableListOf<Call<List<SearchResult>>>()

        if (selectedCategory.contains("All")) {
            calls.add(service.getEvents())
            calls.add(service.getSponsors())
            calls.add(service.getTenants())
        } else {
            if (selectedCategory.contains("Event")) calls.add(service.getEvents())
            if (selectedCategory.contains("Sponsor")) calls.add(service.getSponsors())
            if (selectedCategory.contains("Tenant")) calls.add(service.getTenants())
        }

        var remaining = calls.size
        if (remaining == 0) return

        calls.forEach { call ->
            fetchAndFilter(call) {
                remaining--
                if (remaining == 0) {
                    adapter.submitList(allList)
                }
            }
        }
    }
}