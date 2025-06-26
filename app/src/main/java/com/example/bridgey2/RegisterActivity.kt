package com.example.bridgey2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.bridgey2.databinding.ActivityRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = Firebase.database("https://bridgey-be93a-default-rtdb.firebaseio.com/")
        val usersRef = database.getReference("users")

        setupPasswordToggle()

        with(binding) {
            btnRegister.setOnClickListener {
                val name = etName.text.toString().trim()
                val email = etEmail.text.toString().trim().lowercase()
                val password = etPassword.text.toString().trim()

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this@RegisterActivity, "Harap lengkapi semua data!", Toast.LENGTH_LONG).show()
                } else {
                    usersRef.orderByChild("email").equalTo(email)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()) {
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "Email ini sudah terdaftar. Gunakan email lain.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    val userId = usersRef.push().key
                                    if (userId != null) {
                                        val userData = hashMapOf(
                                            "username" to name,
                                            "email" to email,
                                            "password" to password // NOTE: hash this in real apps
                                        )
                                        usersRef.child(userId).setValue(userData)
                                            .addOnSuccessListener {
                                                Toast.makeText(this@RegisterActivity, "Registrasi berhasil!", Toast.LENGTH_LONG).show()
                                                val gotoLogin = Intent(this@RegisterActivity, LoginActivity::class.java)
                                                startActivity(gotoLogin)
                                                finish()
                                            }
                                            .addOnFailureListener {
                                                Toast.makeText(this@RegisterActivity, "Registrasi gagal: ${it.message}", Toast.LENGTH_LONG).show()
                                            }
                                    } else {
                                        Toast.makeText(this@RegisterActivity, "Gagal membuat ID pengguna.", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Toast.makeText(this@RegisterActivity, "Terjadi kesalahan: ${error.message}", Toast.LENGTH_LONG).show()
                            }
                        })
                }
            }

            tvLoginLink.setOnClickListener {
                val toLogin = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(toLogin)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupPasswordToggle() {
        binding.etPassword.setOnTouchListener { _, event ->
            val drawableEnd = 2 // index drawable end (kanan)
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEndIcon = binding.etPassword.compoundDrawables[drawableEnd]
                drawableEndIcon?.let {
                    val bounds = it.bounds
                    val x = event.rawX.toInt()
                    val right = binding.etPassword.right
                    if (x >= right - bounds.width() - binding.etPassword.paddingEnd) {
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
        binding.etPassword.inputType = if (visible) {
            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        val icon = if (visible) R.drawable.ic_eye_open else R.drawable.ic_eye_closed
        binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(
            null, null,
            ContextCompat.getDrawable(this, icon), null
        )
        binding.etPassword.setSelection(binding.etPassword.text.length)
    }
}
