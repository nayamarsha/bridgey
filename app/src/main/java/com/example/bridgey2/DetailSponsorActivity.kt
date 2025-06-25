package com.example.bridgey2

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.bridgey2.Models.ResponseEvent
import com.example.bridgey2.Models.ResponseSponsor
import com.example.bridgey2.databinding.ActivityDetailBinding
import com.example.bridgey2.databinding.ActivityDetailSponsorBinding

class DetailSponsorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailSponsorBinding
    private lateinit var sponsor: ResponseSponsor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailSponsorBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        intent?.extras?.getParcelable<ResponseSponsor>("SPONSOR_DATA")?.let {
            sponsor = it
        } ?: return
        sponsor = intent.getParcelableExtra<ResponseSponsor>("SPONSOR_DATA") ?: return

        // Tampilkan data ke view
        binding.apply {
            detailName.text = sponsor.name
            valDesc.text = sponsor.description
//            valDate.text = sponsor.date
//            valLocation.text = sponsor.location

            Glide.with(imageView.context)
                .load(sponsor.logo)
                .into(imageView)
        }

        // Button submit
        binding.submitProposal.setOnClickListener {
            val intent = Intent(this@DetailSponsorActivity, PostProposalActivity::class.java)
            startActivity(intent)
        }

        binding.btnClose.setOnClickListener {
            finish()
        }

    }
}
