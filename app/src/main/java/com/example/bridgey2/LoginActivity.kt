package com.example.bridgey2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {

    private lateinit var emailInput: EditText
    private lateinit var passInput: EditText
    private lateinit var loginButton: MaterialButton
    private lateinit var googleButton: MaterialButton
    private lateinit var registerLink: TextView

    private var isPasswordVisible = false
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailInput = findViewById(R.id.emailInput)
        passInput = findViewById(R.id.passInput)
        loginButton = findViewById(R.id.loginButton)
        googleButton = findViewById(R.id.googleButton)
        registerLink = findViewById(R.id.register_link) // sesuaikan dengan ID di XML

        setupPasswordToggle()

        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passInput.text.toString()
            handleLogin(email, password)
        }

        googleButton.setOnClickListener {
            Toast.makeText(this, "Login with Google clicked", Toast.LENGTH_SHORT).show()
        }

        registerLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish() // jangan bisa balik ke login dari main
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupPasswordToggle() {
        passInput.setOnTouchListener { _, event ->
            val drawableEnd = 2
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = passInput.compoundDrawables[drawableEnd]
                drawableEnd?.let {
                    val bounds = it.bounds
                    val x = event.rawX.toInt()
                    val right = passInput.right
                    if (x >= right - bounds.width() - passInput.paddingEnd) {
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
        passInput.inputType = if (visible) {
            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        val icon = if (visible) R.drawable.ic_eye_open else R.drawable.ic_eye_closed
        passInput.setCompoundDrawablesWithIntrinsicBounds(
            null, null,
            ContextCompat.getDrawable(this, icon), null
        )

        passInput.setSelection(passInput.text.length)
    }

    private fun handleLogin(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and password must not be empty", Toast.LENGTH_SHORT).show()
            return
        }

        // Login sukses palsu (mock)
        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
