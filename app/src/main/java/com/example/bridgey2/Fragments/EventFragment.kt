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
        eventAdapter = ScheduleEventAdapter(eventArrayList)
        recyclerView.adapter = eventAdapter

        fetchEvents()
    }

    private fun fetchEvents() {
        db = FirebaseDatabase.getInstance().getReference("events")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                eventArrayList.clear() // clear biar data ga dobel saat refresh

                if (snapshot.exists()) {
                    for (eventSnapshot in snapshot.children) {
                        val event = eventSnapshot.getValue(Post::class.java)
                        event?.id = eventSnapshot.key // ⬅️ ini penting!

                        if (event != null) {
                            eventArrayList.add(event)
                        }
                    }
                    eventAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Database error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
