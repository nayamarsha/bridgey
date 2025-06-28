package com.example.bridgey2.Fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bridgey2.Adapters.SearchAdapter
import com.example.bridgey2.Models.SearchItem
import com.example.bridgey2.Models.SearchItemImpl
import com.example.bridgey2.R
import com.google.firebase.database.*
import java.io.File
import java.io.FileOutputStream

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
    ): View = inflater.inflate(R.layout.fragment_search, container, false)

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

        performSearch()
    }

    private fun setupCategoryButtons() {
        val multiSelectButtons = listOf(btnEvent, btnSponsor, btnTenant)

        fun updateAllButtonState() {
            val allInactive = multiSelectButtons.none { it.isSelected }
            val allActive = multiSelectButtons.all { it.isSelected }

            if (allActive) {
                multiSelectButtons.forEach { it.isSelected = false }
                btnAll.isSelected = true
                selectedCategory = "All"
                performSearch()
            } else if (btnAll.isSelected && !allInactive) {
                btnAll.isSelected = false
            }

            if (allInactive) {
                btnAll.isSelected = true
                selectedCategory = "All"
                performSearch()
            }
        }

        btnAll.setOnClickListener {
            btnAll.isSelected = true
            multiSelectButtons.forEach { it.isSelected = false }
            selectedCategory = "All"
            performSearch()
        }

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

        btnAll.isSelected = true
        multiSelectButtons.forEach { it.isSelected = false }
    }

    private fun performSearch() {
        val query = searchInput.text.toString().trim()
        val resultList = mutableListOf<SearchItem>()

        val selectedPaths = mutableListOf<String>()
        if (selectedCategory.contains("All")) {
            selectedPaths.addAll(listOf("events", "sponsors", "tenants"))
        } else {
            if (selectedCategory.contains("Event")) selectedPaths.add("events")
            if (selectedCategory.contains("Sponsor")) selectedPaths.add("sponsors")
            if (selectedCategory.contains("Tenant")) selectedPaths.add("tenants")
        }

        var remaining = selectedPaths.size
        if (remaining == 0) return

        for (path in selectedPaths) {
            val dbRef = FirebaseDatabase.getInstance().getReference(path)
            dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (child in snapshot.children) {
                        when (path) {
                            "events" -> {
                                val title = child.child("eventTitle").getValue(String::class.java)
                                if (title != null && title.contains(query, ignoreCase = true)) {
                                    val base64Img = child.child("eventImg").getValue(String::class.java)
                                    val imgUri = if (!base64Img.isNullOrEmpty()) base64ToImageUri(base64Img) else null

                                    val item = SearchItemImpl(
                                        name = title,
                                        title = title,
                                        date = child.child("eventDate").getValue(String::class.java),
                                        location = child.child("eventLocation").getValue(String::class.java),
                                        imageUrl = imgUri,
                                        id = child.key,
                                        type = "Event"
                                    )
                                    resultList.add(item)
                                }
                            }

                            "sponsors", "tenants" -> {
                                val name = child.child("name").getValue(String::class.java)
                                if (name != null && name.contains(query, ignoreCase = true)) {
                                    val logo = child.child("logo").getValue(String::class.java)
                                    val item = SearchItemImpl(
                                        name = name,
                                        logo = logo,
                                        imageUrl = logo, // logo dalam bentuk Base64 juga bisa di-decode kalau perlu
                                        id = child.key,
                                        type = if (path == "sponsors") "Sponsor" else "Tenant"
                                    )
                                    resultList.add(item)
                                }
                            }
                        }
                    }

                    remaining--
                    if (remaining == 0) {
                        adapter.submitList(resultList)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    remaining--
                    if (remaining == 0) {
                        adapter.submitList(resultList)
                    }
                }
            })
        }
    }

    private fun base64ToImageUri(base64: String): String? {
        return try {
            val bytes = android.util.Base64.decode(base64, android.util.Base64.DEFAULT)
            val bitmap = android.graphics.BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

            val filename = "temp_${System.currentTimeMillis()}.png"
            val file = File(requireContext().cacheDir, filename)
            val outputStream = FileOutputStream(file)
            bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            Uri.fromFile(file).toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
