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
import com.example.bridgey2.Adapters.ScheduleTenantAdapter
import com.example.bridgey2.Models.Tenant
import com.example.bridgey2.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

class TenantFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var tenantAdapter: ScheduleTenantAdapter
    private lateinit var db: DatabaseReference
    private lateinit var tenantArrayList: ArrayList<Tenant>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tenant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        tenantArrayList = arrayListOf()
        tenantAdapter = ScheduleTenantAdapter(tenantArrayList)
        recyclerView.adapter = tenantAdapter

        fetchTenants()
    }

    private fun fetchTenants() {
        db = FirebaseDatabase.getInstance().getReference("tenants")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tenantArrayList.clear()
                if (snapshot.exists()) {
                    for (dataSnap in snapshot.children) {
                        val tenant = dataSnap.getValue(Tenant::class.java)
                        tenant?.let {
                            it.id = dataSnap.key
                            tenantArrayList.add(it) }
                    }

                    tenantAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Database error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}