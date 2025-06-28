package com.example.bridgey2.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bridgey2.Adapters.ScheduleSponsorAdapter
import com.example.bridgey2.Models.Sponsor
import com.example.bridgey2.R
import com.google.firebase.database.*

class SponsorFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var sponsorAdapter: ScheduleSponsorAdapter
    private lateinit var sponsorList: ArrayList<Sponsor>
    private lateinit var dbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_sponsor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        sponsorList = arrayListOf()
        sponsorAdapter = ScheduleSponsorAdapter(sponsorList)
        recyclerView.adapter = sponsorAdapter

        fetchSponsors()
    }

    private fun fetchSponsors() {
        dbRef = FirebaseDatabase.getInstance().getReference("sponsors")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                sponsorList.clear()

                if (snapshot.exists()) {
                    for (dataSnap in snapshot.children) {
                        val sponsor = dataSnap.getValue(Sponsor::class.java)
                        sponsor?.let {
                            it.id = dataSnap.key
                            sponsorList.add(it) }
                    }
                    sponsorAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
