package com.example.bridgey2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.bridgey2.databinding.ActivityLoginBinding // Import kelas binding yang dihasilkan
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.database
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseReference // Import DatabaseReference

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi Firebase Realtime Database
        val database = Firebase.database("https://bridgey-be93a-default-rtdb.firebaseio.com/")
        val usersRef = database.getReference("users")

        setupPasswordToggle()

        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passInput.text.toString()
            handleLogin(email, password, usersRef)
        }

        binding.googleButton.setOnClickListener {
            Toast.makeText(this, "Login with Google clicked", Toast.LENGTH_SHORT).show()
        }

        binding.registerLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun setupPasswordToggle() {
        binding.passInput.setOnTouchListener { _, event ->
            val drawableEnd = 2
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEndIcon = binding.passInput.compoundDrawables[drawableEnd]
                drawableEndIcon?.let {
                    val bounds = it.bounds
                    val x = event.rawX.toInt()
                    val right = binding.passInput.right
                    if (x >= right - bounds.width() - binding.passInput.paddingEnd) {
                        isPasswordVisible = !isPasswordVisible
                        togglePasswordVisibility(isPasswordVisible)
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }
    }


    private fun togglePasswordVisibility(visible: Boolean) {
        binding.passInput.inputType = if (visible) {
            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        val icon = if (visible) R.drawable.ic_eye_open else R.drawable.ic_eye_closed
        binding.passInput.setCompoundDrawablesWithIntrinsicBounds(
            null, null,
            ContextCompat.getDrawable(this, icon), null
        )

        binding.passInput.setSelection(binding.passInput.text.length)
    }

    private fun handleLogin(email: String, password: String, usersRef: DatabaseReference) {

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email dan password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            return
        }

        // Melakukan query ke Firebase untuk mencari user berdasarkan email
        usersRef.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        var userFound = false
                        for (userSnapshot in snapshot.children) {
                            val storedPassword = userSnapshot.child("password").getValue(String::class.java)
                            if (storedPassword == password) {
                                userFound = true
                                Toast.makeText(this@LoginActivity, "Login berhasil!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                                break
                            }
                        }

                        if (!userFound) {
                            Toast.makeText(this@LoginActivity, "Password salah!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Email tidak terdaftar!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@LoginActivity, "Kesalahan database: ${error.message}", Toast.LENGTH_LONG).show()
                }
            })
    }
}
