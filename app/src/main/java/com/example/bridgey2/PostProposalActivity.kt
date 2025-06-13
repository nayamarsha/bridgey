package com.example.bridgey2.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bridgey2.MainActivity
import com.example.bridgey2.R

class PostProposalActivity : AppCompatActivity() {

    private lateinit var btnPost: Button
    private lateinit var btnCancel: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_post)

        // Inisialisasi button
        btnPost = findViewById(R.id.postButton)
        btnCancel = findViewById(R.id.iconCancel)

        // Event klik tombol POST
        btnPost.setOnClickListener {
            Toast.makeText(this, "Post Berhasil!", Toast.LENGTH_SHORT).show()

            // Balik ke MainActivity dan minta navigasi ke HomeFragment
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("navigate_to", "home")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        // Event klik tombol Cancel
        btnCancel.setOnClickListener {
            // Kembali ke DetailActivity
            finish()
        }
    }
}
