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

    private lateinit var binding: ActivityLoginBinding // Deklarasikan variabel binding

    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen() // Memasang splash screen
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater) // Menginisialisasi objek binding
        setContentView(binding.root) // Mengatur tampilan konten menggunakan root dari binding

        // Inisialisasi Firebase Realtime Database
        val database = Firebase.database("https://bridgey-be93a-default-rtdb.firebaseio.com/")
        val usersRef = database.getReference("users") // Mendapatkan referensi ke node "users"

        setupPasswordToggle() // Memanggil fungsi untuk mengatur toggle visibilitas password

        // Menyiapkan onClickListener untuk tombol login
        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim() // Ambil email dari input dan hapus spasi
            val password = binding.passInput.text.toString() // Ambil password dari input
            handleLogin(email, password, usersRef) // Memanggil fungsi handleLogin dengan email, password, dan referensi database
        }

        // Menyiapkan onClickListener untuk tombol login dengan Google (jika diimplementasikan)
        binding.googleButton.setOnClickListener {
            Toast.makeText(this, "Login with Google clicked", Toast.LENGTH_SHORT).show()
            // Implementasi logika login Google di sini
        }

        // Menyiapkan onClickListener untuk link register
        binding.registerLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java) // Membuat Intent ke RegisterActivity
            startActivity(intent) // Memulai RegisterActivity
            finish() // Menutup LoginActivity agar tidak bisa kembali dengan tombol back dari RegisterActivity
        }
    }

    /**
     * Mengatur fungsionalitas toggle visibilitas password.
     * Menggunakan setOnTouchListener pada EditText password untuk mendeteksi sentuhan pada drawable end.
     */
    @SuppressLint("ClickableViewAccessibility")
    private fun setupPasswordToggle() {
        binding.passInput.setOnTouchListener { _, event ->
            val drawableEnd = 2 // Indeks untuk drawable di posisi end (kanan)
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEndIcon = binding.passInput.compoundDrawables[drawableEnd]
                drawableEndIcon?.let {
                    val bounds = it.bounds // Mendapatkan batas (bounds) dari drawable
                    val x = event.rawX.toInt() // Posisi X sentuhan relatif terhadap layar
                    val right = binding.passInput.right // Batas kanan dari EditText
                    // Memeriksa apakah sentuhan terjadi di dalam area drawable end
                    if (x >= right - bounds.width() - binding.passInput.paddingEnd) {
                        isPasswordVisible = !isPasswordVisible // Toggle status visibilitas password
                        togglePasswordVisibility(isPasswordVisible) // Memanggil fungsi untuk mengubah visibilitas
                        return@setOnTouchListener true // Mengkonsumsi event sentuhan
                    }
                }
            }
            false // Event tidak dikonsumsi
        }
    }

    /**
     * Mengubah visibilitas teks password pada EditText.
     * Mengatur inputType dan icon drawable end sesuai dengan status visibilitas.
     * @param visible true jika password harus terlihat, false jika tidak.
     */
    private fun togglePasswordVisibility(visible: Boolean) {
        binding.passInput.inputType = if (visible) {
            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD // Teks password terlihat
        } else {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD // Teks password tersembunyi
        }

        val icon = if (visible) R.drawable.ic_eye_open else R.drawable.ic_eye_closed // Pilih icon mata
        binding.passInput.setCompoundDrawablesWithIntrinsicBounds(
            null, null,
            ContextCompat.getDrawable(this, icon), null // Mengatur drawable end (kanan)
        )

        // Mengatur kursor ke akhir teks setelah perubahan inputType
        binding.passInput.setSelection(binding.passInput.text.length)
    }

    /**
     * Menangani logika login user dengan memeriksa email dan password di Firebase Realtime Database.
     * @param email Email yang dimasukkan user.
     * @param password Password yang dimasukkan user.
     * @param usersRef Referensi ke node "users" di database Firebase.
     */
    private fun handleLogin(email: String, password: String, usersRef: DatabaseReference) {
        // Validasi input kosong
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email dan password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            return
        }

        // Melakukan query ke Firebase untuk mencari user berdasarkan email
        usersRef.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        // Email ditemukan di database
                        var userFound = false
                        // Iterasi melalui hasil snapshot (meskipun seharusnya hanya ada satu jika email unik)
                        for (userSnapshot in snapshot.children) {
                            val storedPassword = userSnapshot.child("password").getValue(String::class.java)
                            // Membandingkan password yang dimasukkan dengan password yang tersimpan
                            if (storedPassword == password) {
                                // Password cocok, login berhasil
                                userFound = true
                                Toast.makeText(this@LoginActivity, "Login berhasil!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent) // Memulai MainActivity
                                finish() // Menutup LoginActivity agar tidak bisa kembali dengan tombol back
                                break // Keluar dari loop setelah user ditemukan
                            }
                        }

                        if (!userFound) {
                            // Email ditemukan tetapi password tidak cocok
                            Toast.makeText(this@LoginActivity, "Password salah!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Email tidak ditemukan di database
                        Toast.makeText(this@LoginActivity, "Email tidak terdaftar!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Penanganan kesalahan jika query database dibatalkan atau terjadi error
                    Toast.makeText(this@LoginActivity, "Kesalahan database: ${error.message}", Toast.LENGTH_LONG).show()
                }
            })
    }
}
