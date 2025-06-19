package com.example.bridgey2

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var googleSignUpLayout: LinearLayout
    private lateinit var loginTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inisialisasi komponen UI
        nameEditText = findViewById(R.id.et_name)
        emailEditText = findViewById(R.id.et_email)
        passwordEditText = findViewById(R.id.et_password)
        registerButton = findViewById(R.id.btn_register)
        googleSignUpLayout = findViewById(R.id.btn_sign_up_google_layout)
        loginTextView = findViewById(R.id.tv_login_link)

        // Aksi saat tombol Register diklik
        registerButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Lakukan aksi registrasi (misalnya kirim ke server atau Firebase)
                Toast.makeText(this, "Registering $name...", Toast.LENGTH_SHORT).show()
            }
        }

        // Aksi saat tombol Google Sign Up diklik
        googleSignUpLayout.setOnClickListener {
            // Di sini bisa panggil Google Sign-In API
            Toast.makeText(this, "Google Sign-Up Clicked", Toast.LENGTH_SHORT).show()
        }

        // Aksi saat link Login diklik
        loginTextView.setOnClickListener {
            // Pindah ke activity login
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
