package com.example.bridgey2.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bridgey2.Adapters.ScheduleEventAdapter
import com.example.bridgey2.Models.Post
import com.example.bridgey2.R
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class EventFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventAdapter: ScheduleEventAdapter
    private lateinit var db: DatabaseReference
    private lateinit var eventArrayList: ArrayList<Post>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        eventArrayList = arrayListOf()
        eventAdapter = ScheduleEventAdapter(arrayListOf()) // kosong dulu
        recyclerView.adapter = eventAdapter

        fetchEvents()
    }

    private fun fetchEvents() {
        db = FirebaseDatabase.getInstance().getReference("events")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                eventArrayList.clear()
                if (snapshot.exists()) {
                    for (eventSnapshot in snapshot.children) {
                        val event = eventSnapshot.getValue(Post::class.java)
                        event?.id = eventSnapshot.key

                        if (event != null) {
                            eventArrayList.add(event)
                        }
                    }

                    // Urutkan berdasarkan tanggal terbaru
                    val sortedList = sortEventsByDate(eventArrayList)

                    // Update adapter dengan data yang sudah diurutkan
                    eventAdapter = ScheduleEventAdapter(sortedList)
                    recyclerView.adapter = eventAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Database error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun sortEventsByDate(events: ArrayList<Post>): ArrayList<Post> {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return ArrayList(events.sortedByDescending {
            try {
                sdf.parse(it.eventDate ?: "") ?: sdf.parse("01/01/1970")
            } catch (e: Exception) {
                sdf.parse("01/01/1970")
            }
        })
    }
}
