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
import com.example.bridgey2.Models.ResponseSponsor
import com.example.bridgey2.Adapters.EventAdapter
import com.example.bridgey2.Adapters.SponsorAdapter
import com.example.bridgey2.R
import com.example.bridgey2.AccountActivity
import com.example.bridgey2.Adapters.TenantAdapter
import com.example.bridgey2.Models.Post
import com.example.bridgey2.Models.Sponsor
import com.example.bridgey2.Models.Tenant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var db: DatabaseReference
    private lateinit var eventArrayList: ArrayList<Post>
    private lateinit var sponsorArrayList: ArrayList<Sponsor>
    private lateinit var tenantArrayList: ArrayList<Tenant>

    private lateinit var rvEvent: RecyclerView
    private lateinit var rvSponsor: RecyclerView
    private lateinit var rvTenant: RecyclerView

    private lateinit var logoButton: View

    private lateinit var eventAdapter: EventAdapter
    private lateinit var sponsorAdapter: SponsorAdapter
    private lateinit var tenantAdapter: TenantAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventArrayList = arrayListOf<Post>()
        sponsorArrayList = arrayListOf<Sponsor>()
        tenantArrayList = arrayListOf<Tenant>()

        rvEvent = view.findViewById(R.id.rv_event)
        rvEvent.hasFixedSize()
        rvSponsor = view.findViewById(R.id.rv_sponsor)
        rvSponsor.hasFixedSize()
        rvTenant = view.findViewById(R.id.rv_tenant)
        rvTenant.hasFixedSize()

        logoButton = view.findViewById(R.id.logo)

        rvEvent.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvSponsor.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvTenant.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        fetchEventData()
        fetchSponsorData()
        fetchTenantData()

        logoButton.setOnClickListener {
            val intent = Intent(requireContext(), AccountActivity::class.java)
            startActivity(intent)
        }
    }

//    private fun fetchEventData() {
//        ApiConfig.getService().getProducts().enqueue(object : Callback<List<ResponseEvent>> {
//            override fun onResponse(call: Call<List<ResponseEvent>>, response: Response<List<ResponseEvent>>) {
//                if (response.isSuccessful) {
//                    response.body()?.let { eventList ->
//                        eventAdapter = EventAdapter(eventList)
//                        rvEvent.adapter = eventAdapter
//                    }
//                } else {
//                    Toast.makeText(requireContext(), "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<List<ResponseEvent>>, t: Throwable) {
//                Toast.makeText(requireContext(), t.localizedMessage, Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
    private fun fetchEventData() {
        db = FirebaseDatabase.getInstance().getReference("events")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(eventSnapshot in snapshot.children){
                        val event = eventSnapshot.getValue(Post::class.java)
                        if (event != null) {
                            event.id = eventSnapshot.key  // ✅ Simpan unique key ke field id
                            eventArrayList.add(event)
                        }
                    }
                    eventAdapter = EventAdapter(eventArrayList)
                    rvEvent.adapter = eventAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Database error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchSponsorData() {
        db = FirebaseDatabase.getInstance().getReference("sponsors")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(eventSnapshot in snapshot.children){
                        val sponsor = eventSnapshot.getValue(Sponsor::class.java)
                        if (sponsor != null) {
                            sponsor.id = eventSnapshot.key  // ✅ Simpan unique key ke field id
                            sponsorArrayList.add(sponsor)
                        }
                    }
                    sponsorAdapter = SponsorAdapter(sponsorArrayList)
                    rvSponsor.adapter = sponsorAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Database error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchTenantData() {
        db = FirebaseDatabase.getInstance().getReference("tenants")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(tenantSnapshot in snapshot.children){
                        val tenant = tenantSnapshot.getValue(Tenant::class.java)
                        if (tenant != null) {
                            tenant.id = tenantSnapshot.key  // ✅ Simpan unique key ke field id
                            tenantArrayList.add(tenant)
                        }
                    }
                    tenantAdapter = TenantAdapter(tenantArrayList)
                    rvTenant.adapter = tenantAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Database error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}

