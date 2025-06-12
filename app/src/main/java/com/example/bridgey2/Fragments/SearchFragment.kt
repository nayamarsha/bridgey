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
import com.example.bridgey.ui.search.adapter.SearchAdapter
import com.google.firebase.appdistribution.gradle.ApiService
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
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

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
        val allButtons = listOf(btnAll, btnEvent, btnSponsor, btnTenant)

        fun updateSelected(button: Button) {
            allButtons.forEach { it.isSelected = false }
            button.isSelected = true
            selectedCategory = button.text.toString()
            performSearch()
        }

        btnAll.setOnClickListener { updateSelected(btnAll) }
        btnEvent.setOnClickListener { updateSelected(btnEvent) }
        btnSponsor.setOnClickListener { updateSelected(btnSponsor) }
        btnTenant.setOnClickListener { updateSelected(btnTenant) }

        // Default selected
        btnAll.isSelected = true
    }

    private fun performSearch() {
        val query = searchInput.text.toString()

        ApiConfig.getService().search(query, selectedCategory).enqueue(object : Callback<List<SearchResult>> {
            override fun onResponse(call: Call<List<SearchResult>>, response: Response<List<SearchResult>>) {
                if (response.isSuccessful) {
                    val results = response.body() ?: emptyList()
                    adapter.submitList(results)
                }
            }

            override fun onFailure(call: Call<List<SearchResult>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}