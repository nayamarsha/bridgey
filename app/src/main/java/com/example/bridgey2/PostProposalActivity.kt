package com.example.bridgey2

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import androidx.navigation.findNavController

class PostProposalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_post_proposal)

        // Ambil root view dengan ID rootLayout
        val rootView = findViewById<View>(R.id.rootLayout)

        // Terapkan padding sesuai sistem bar dan status bar
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<ImageButton>(R.id.iconCancel).setOnClickListener {
            finish()
        }

        findViewById<MaterialButton>(R.id.postButton).setOnClickListener {
            finish()
        }
    }
}
