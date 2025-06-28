package com.example.bridgey2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class PostProposalActivity : AppCompatActivity() {

    private lateinit var subjectEditText: EditText
    private lateinit var linkEditText: EditText
    private var sponsorEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_post_proposal)

        val rootView = findViewById<View>(R.id.rootLayout)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        subjectEditText = findViewById(R.id.description)
        linkEditText = findViewById(R.id.tags)
        sponsorEmail = intent.getStringExtra("SPONSOR_EMAIL")

        findViewById<ImageButton>(R.id.iconCancel).setOnClickListener {
            finish()
        }

        findViewById<MaterialButton>(R.id.postButton).setOnClickListener {
            sendEmail()
        }
    }

    private fun sendEmail() {
        val subjectText = subjectEditText.text.toString().trim()
        val linkText = linkEditText.text.toString().trim()

        if (sponsorEmail.isNullOrEmpty()) {
            Toast.makeText(this, "Email sponsor tidak tersedia", Toast.LENGTH_SHORT).show()
            return
        }

        if (subjectText.isEmpty() || linkText.isEmpty()) {
            Toast.makeText(this, "Subject dan Link tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }

        val subjectEncoded = Uri.encode("Proposal: $subjectText")
        val bodyEncoded = Uri.encode(
            "Dengan hormat,\n\nSaya mengajukan proposal kerja sama yang dapat Bapak/Ibu tinjau melalui tautan berikut:\n$linkText\n\nProposal ini saya ajukan melalui aplikasi Bridgey sebagai bagian dari inisiatif kami untuk menjalin kolaborasi yang saling menguntungkan.\n\nSaya sangat menghargai waktu dan perhatian Bapak/Ibu dalam meninjau proposal ini. Atas perhatiannya, saya ucapkan terima kasih."
        )

        val mailtoUri = Uri.parse("mailto:$sponsorEmail?subject=$subjectEncoded&body=$bodyEncoded")

        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = mailtoUri
        }

        try {
            startActivity(emailIntent)
        } catch (e: Exception) {
            Toast.makeText(this, "Tidak ada aplikasi email yang tersedia", Toast.LENGTH_SHORT).show()
        }
    }
}
