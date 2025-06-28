package com.example.bridgey2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.bridgey2.databinding.ActivityDetailSponsorBinding
import com.google.firebase.database.*

class DetailSponsorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailSponsorBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailSponsorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle status bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sponsorId = intent.getStringExtra("SPONSOR_ID")
        if (sponsorId == null) {
            Toast.makeText(this, "Sponsor ID tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Ambil data sponsor dari Firebase
        database = FirebaseDatabase.getInstance().getReference("sponsors").child(sponsorId)
        database.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val name = snapshot.child("name").getValue(String::class.java)
                val description = snapshot.child("description").getValue(String::class.java)
                val logo = snapshot.child("logo").getValue(String::class.java)
                val email = snapshot.child("email").getValue(String::class.java)

                binding.detailName.text = name
                binding.valDesc.text = description

                Glide.with(this)
                    .load(logo)
                    .into(binding.imageView)

                // Tombol submit proposal
                binding.submitProposal.setOnClickListener {
                    val intent = Intent(this, PostProposalActivity::class.java)
                    intent.putExtra("SPONSOR_EMAIL", email)
                    startActivity(intent)
                }

            } else {
                Toast.makeText(this, "Sponsor tidak ditemukan", Toast.LENGTH_SHORT).show()
                finish()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Gagal mengambil data sponsor", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Tombol tutup
        binding.btnClose.setOnClickListener {
            finish()
        }
    }
}
