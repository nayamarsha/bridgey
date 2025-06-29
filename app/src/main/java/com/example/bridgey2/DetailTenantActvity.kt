package com.example.bridgey2

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.bridgey2.databinding.ActivityDetailBinding
import com.example.bridgey2.databinding.ActivityDetailSponsorBinding
import com.example.bridgey2.databinding.ActivityDetailTenantActvityBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailTenantActvity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTenantActvityBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailTenantActvityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle status bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tenantId = intent.getStringExtra("TENANT_ID")
        if (tenantId == null) {
            Toast.makeText(this, "Tenant ID tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Ambil data sponsor dari Firebase
        database = FirebaseDatabase.getInstance().getReference("tenants").child(tenantId)
        database.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val name = snapshot.child("name").getValue(String::class.java)
                val description = snapshot.child("description").getValue(String::class.java)
                val logo = snapshot.child("logo").getValue(String::class.java)

                binding.detailName.text = name
                binding.valDesc.text = description

                Glide.with(this)
                    .load(logo)
                    .into(binding.imageView)


            } else {
                Toast.makeText(this, "Tenant tidak ditemukan", Toast.LENGTH_SHORT).show()
                finish()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Gagal mengambil data tenant", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Tombol tutup
        binding.btnClose.setOnClickListener {
            finish()
        }
    }
}