package com.example.yourapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bridgey2.R

class AccountActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvRole: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvFeeds: TextView
    private lateinit var tvMentions: TextView
    private lateinit var rvFeeds: RecyclerView
    private lateinit var btnEditProfile: Button
    private lateinit var ivBack: ImageView
    private lateinit var ivSettings: ImageView

    private lateinit var feedAdapter: FeedAdapter
    private var isFeedsSelected = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        tvName = findViewById(R.id.tvName)
        tvRole = findViewById(R.id.tvRole)
        tvPhone = findViewById(R.id.tvPhone)
        tvFeeds = findViewById(R.id.tvFeeds)
        tvMentions = findViewById(R.id.tvMentions)
        rvFeeds = findViewById(R.id.rvFeeds)
        btnEditProfile = findViewById(R.id.btnEditProfile)
        ivBack = findViewById(R.id.ivBack)
        ivSettings = findViewById(R.id.ivSettings)

        // Isi awal
        updateProfile(intent)

        // RecyclerView setup
        rvFeeds.layoutManager = LinearLayoutManager(this)
        feedAdapter = FeedAdapter(getFeeds())
        rvFeeds.adapter = feedAdapter

        // Edit Profile
        btnEditProfile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            intent.putExtra("name", tvName.text.toString())
            intent.putExtra("role", tvRole.text.toString())
            intent.putExtra("phone", tvPhone.text.toString())
            startActivity(intent)
        }

        tvFeeds.setOnClickListener {
            isFeedsSelected = true
            updateTabs()
        }

        tvMentions.setOnClickListener {
            isFeedsSelected = false
            updateTabs()
        }

        ivBack.setOnClickListener { finish() }
        ivSettings.setOnClickListener { /* Tambahkan fitur nanti */ }
    }

    private fun updateProfile(intent: Intent) {
        tvName.text = intent.getStringExtra("name") ?: "Budiono Siregar"
        tvRole.text = intent.getStringExtra("role") ?: "Event Organizer"
        tvPhone.text = intent.getStringExtra("phone") ?: "+6285819040610"
    }

    override fun onResume() {
        super.onResume()
        updateProfile(intent)
    }

    private fun updateTabs() {
        if (isFeedsSelected) {
            tvFeeds.setBackgroundColor(getColor(R.color.selected_tab))
            tvMentions.setBackgroundColor(getColor(android.R.color.transparent))
            feedAdapter.updateData(getFeeds())
        } else {
            tvFeeds.setBackgroundColor(getColor(android.R.color.transparent))
            tvMentions.setBackgroundColor(getColor(R.color.selected_tab))
            feedAdapter.updateData(getMentions())
        }
    }

    private fun getFeeds(): List<FeedItem> {
        return listOf(
            FeedItem("Feed 1", "Deskripsi feed 1"),
            FeedItem("Feed 2", "Deskripsi feed 2")
        )
    }

    private fun getMentions(): List<FeedItem> {
        return listOf(
            FeedItem("Mention 1", "Deskripsi mention 1"),
            FeedItem("Mention 2", "Deskripsi mention 2")
        )
    }
}
