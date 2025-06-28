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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailSponsorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailSponsorBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailSponsorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Ambil ID dari Intent
        val sponsorId = intent.getStringExtra("SPONSOR_ID")
        if (sponsorId == null) {
            Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Ambil data dari Firebase
        database = FirebaseDatabase.getInstance().getReference("sponsors").child(sponsorId)
        database.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val name = snapshot.child("name").getValue(String::class.java)
                val desc = snapshot.child("description").getValue(String::class.java)
                val logoUrl = snapshot.child("logo").getValue(String::class.java)

                binding.detailName.text = name
                binding.valDesc.text = desc

                // Tampilkan gambar dengan Glide
                if (!logoUrl.isNullOrEmpty()) {
                    Glide.with(this)
                        .load(logoUrl)
                        .into(binding.imageView)
                }
            } else {
                Toast.makeText(this, "Data tidak tersedia", Toast.LENGTH_SHORT).show()
                finish()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.submitProposal.setOnClickListener {
            val intent = Intent(this@DetailSponsorActivity, PostProposalActivity::class.java)
            startActivity(intent)
        }

        binding.btnClose.setOnClickListener {
            finish()
        }
    }
}
