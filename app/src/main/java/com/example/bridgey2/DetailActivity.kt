package com.example.bridgey2

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bridgey2.databinding.ActivityDetailBinding
import com.google.firebase.database.*

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Ambil ID dari Intent
        val postId = intent.getStringExtra("POST_ID")
        if (postId == null) {
            Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Ambil data dari Firebase
        database = FirebaseDatabase.getInstance().getReference("events").child(postId)
        database.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val title = snapshot.child("eventTitle").getValue(String::class.java)
                val desc = snapshot.child("eventDesc").getValue(String::class.java)
                val date = snapshot.child("eventDate").getValue(String::class.java)
                val loc = snapshot.child("eventLocation").getValue(String::class.java)
                val imgBase64 = snapshot.child("eventImg").getValue(String::class.java)

                binding.detailName.text = title
                binding.valDesc.text = desc
                binding.valDate.text = date
                binding.valLocation.text = loc

                if (!imgBase64.isNullOrEmpty()) {
                    try {
                        val bytes = android.util.Base64.decode(imgBase64, android.util.Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                        binding.imageView.setImageBitmap(bitmap)
                    } catch (e: Exception) {
                        Toast.makeText(this, "Gagal menampilkan gambar", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Data tidak tersedia", Toast.LENGTH_SHORT).show()
                finish()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnClose.setOnClickListener {
            finish()
        }
    }
}
