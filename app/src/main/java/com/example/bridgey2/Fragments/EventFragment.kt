package com.example.bridgey2.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bridgey2.Adapters.EventAdapter
import com.example.bridgey2.Adapters.ScheduleEventAdapter
import com.example.bridgey2.Api.ApiConfig
import com.example.bridgey2.Api.ApiService
import com.example.bridgey2.Models.ResponseEvent
import com.example.bridgey2.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventAdapter: ScheduleEventAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        fetchEvents()
    }

    private fun fetchEvents() {
        ApiConfig.getService().getProducts().enqueue(object : Callback<List<ResponseEvent>> {
            override fun onResponse(call: Call<List<ResponseEvent>>, response: Response<List<ResponseEvent>>) {
                if (response.isSuccessful) {
                    response.body()?.let { products ->
                        eventAdapter = ScheduleEventAdapter(products)
                        recyclerView.adapter = eventAdapter
                    }
                } else {
                    Toast.makeText(context, "Failed to fetch events", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ResponseEvent>>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
