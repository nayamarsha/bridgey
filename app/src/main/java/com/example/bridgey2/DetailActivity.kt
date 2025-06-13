package com.example.bridgey2

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.bridgey2.Models.ResponseEvent
import com.example.bridgey2.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var event: ResponseEvent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle padding insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Ambil data event dari intent
        event = intent.getParcelableExtra<ResponseEvent>("EVENT_DATA") ?: return

        // Tampilkan data ke view
        binding.apply {
            detailName.text = event.name
            valDesc.text = event.description
            valDate.text = event.date
            valLocation.text = event.location

            Glide.with(imageView.context)
                .load(event.imageUrl)
                .into(imageView)
        }

        // Button submit
        binding.submitProposal.setOnClickListener {
            val intent = Intent(this@DetailActivity, PostProposalActivity::class.java)
            startActivity(intent)
        }
    }
}
