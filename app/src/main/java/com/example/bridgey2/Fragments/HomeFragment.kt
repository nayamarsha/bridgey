package com.example.bridgey2.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bridgey2.Api.ApiConfig
import com.example.bridgey2.Models.ResponseEvent
import com.example.bridgey2.Models.ResponseSponsor
import com.example.bridgey2.Adapters.EventAdapter
import com.example.bridgey2.Adapters.SponsorAdapter
import com.example.bridgey2.R
import com.example.bridgey2.AccountActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var rvEvent: RecyclerView
    private lateinit var rvSponsor: RecyclerView
    private lateinit var logoButton: View

    private lateinit var eventAdapter: EventAdapter
    private lateinit var sponsorAdapter: SponsorAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvEvent = view.findViewById(R.id.rv_event)
        rvSponsor = view.findViewById(R.id.rv_sponsor)
        logoButton = view.findViewById(R.id.logo)

        rvEvent.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvSponsor.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        fetchEventData()
        fetchSponsorData()

        logoButton.setOnClickListener {
            val intent = Intent(requireContext(), AccountActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchEventData() {
        ApiConfig.getService().getProducts().enqueue(object : Callback<List<ResponseEvent>> {
            override fun onResponse(call: Call<List<ResponseEvent>>, response: Response<List<ResponseEvent>>) {
                if (response.isSuccessful) {
                    response.body()?.let { eventList ->
                        eventAdapter = EventAdapter(eventList)
                        rvEvent.adapter = eventAdapter
                    }
                } else {
                    Toast.makeText(requireContext(), "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ResponseEvent>>, t: Throwable) {
                Toast.makeText(requireContext(), t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchSponsorData() {
        ApiConfig.getService().getSponsorsOnly().enqueue(object : Callback<List<ResponseSponsor>> {
            override fun onResponse(call: Call<List<ResponseSponsor>>, response: Response<List<ResponseSponsor>>) {
                if (response.isSuccessful) {
                    response.body()?.let { sponsorList ->
                        sponsorAdapter = SponsorAdapter(sponsorList)
                        rvSponsor.adapter = sponsorAdapter
                    }
                } else {
                    Toast.makeText(requireContext(), "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ResponseSponsor>>, t: Throwable) {
                Toast.makeText(requireContext(), t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

}

