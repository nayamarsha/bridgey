package com.example.bridgey2

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.bridgey2.Models.Post
import com.example.bridgey2.Models.ResponseEvent
import com.example.bridgey2.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var event: Post

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
        intent?.extras?.getParcelable<Post>("EVENT_DATA")?.let {
            event = it
        } ?: return
        event = intent.getParcelableExtra<Post>("EVENT_DATA") ?: return

        // Tampilkan data ke view
        binding.apply {
            detailName.text = event.eventTitle
            valDesc.text = event.eventDesc
            valDate.text = event.eventDate
            valLocation.text = event.eventLocation
            val bytes = android.util.Base64.decode(event.eventImg,
                android.util.Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            imageView.setImageBitmap(bitmap)

//            Glide.with(imageView.context)
//                .load(event.imageUrl)
//                .into(imageView)
        }

        binding.btnClose.setOnClickListener {
            finish()
        }

    }
}
