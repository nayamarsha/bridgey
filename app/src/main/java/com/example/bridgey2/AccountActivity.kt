package com.example.bridgey2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bridgey2.R
import com.example.bridgey2.EditProfileActivity

import com.example.bridgey2.FeedAdapter


class AccountActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvRole: TextView
    private lateinit var tvPhone: TextView
    private lateinit var rvFeeds: RecyclerView
    private lateinit var btnEditProfile: Button
    private lateinit var ivBack: ImageView
    private lateinit var ivSettings: ImageView

    private lateinit var feedAdapter: FeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        tvName = findViewById(R.id.tvName)
        tvRole = findViewById(R.id.tvRole)
        tvPhone = findViewById(R.id.tvPhone)
        rvFeeds = findViewById(R.id.rvFeeds)
        btnEditProfile = findViewById(R.id.btnEditProfile)
        ivBack = findViewById(R.id.ivBack)
        ivSettings = findViewById(R.id.ivSettings)

        // Isi data profil
        updateProfile(intent)

        // Setup RecyclerView
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

        ivSettings.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        ivBack.setOnClickListener { finish() }

    }

    private fun updateProfile(intent: Intent) {
        tvName.text = intent.getStringExtra("name") ?: "Bridgey IF C Kelompok 5"
        tvRole.text = intent.getStringExtra("role") ?: "Event Organizer"
        tvPhone.text = intent.getStringExtra("phone") ?: "+6285819040610"
    }

    override fun onResume() {
        super.onResume()
        updateProfile(intent)
    }

    private fun getFeeds(): List<FeedItem> {
        return listOf(
            FeedItem("https://venngage-wordpress.s3.amazonaws.com/uploads/2023/06/pink-and-blue-recycle-event-volunteer-poster-791x1024.png")
        )
    }
}
