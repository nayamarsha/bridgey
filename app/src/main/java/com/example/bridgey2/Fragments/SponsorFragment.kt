package com.example.bridgey2.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bridgey2.Adapters.ScheduleEventAdapter
import com.example.bridgey2.Adapters.ScheduleSponsorAdapter
import com.example.bridgey2.Api.ApiConfig
import com.example.bridgey2.Models.ResponseEvent
import com.example.bridgey2.Models.ResponseSponsor
import com.example.bridgey2.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SponsorFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var sponsorAdapter: ScheduleSponsorAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sponsor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        fetchSponsors()
    }

    private fun fetchSponsors() {
        ApiConfig.getService().getSponsorsOnly().enqueue(object : Callback<List<ResponseSponsor>> {
            override fun onResponse(call: Call<List<ResponseSponsor>>, response: Response<List<ResponseSponsor>>) {
                if (response.isSuccessful) {
                    response.body()?.let { sponsors ->
                        sponsorAdapter = ScheduleSponsorAdapter(sponsors)
                        recyclerView.adapter = sponsorAdapter
                    }
                } else {
                    Toast.makeText(context, "Failed to fetch events", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ResponseSponsor>>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}