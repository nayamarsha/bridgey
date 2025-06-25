package com.example.bridgey2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.bridgey2.AccountActivity
import com.example.bridgey2.R

class EditProfileActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etRole: EditText
    private lateinit var etPhone: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        etName = findViewById(R.id.etName)
        etRole = findViewById(R.id.etRole)
        etPhone = findViewById(R.id.etPhone)
        btnSave = findViewById(R.id.btnSave)

        etName.setText(intent.getStringExtra("name"))
        etRole.setText(intent.getStringExtra("role"))
        etPhone.setText(intent.getStringExtra("phone"))

        btnSave.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            intent.putExtra("name", etName.text.toString())
            intent.putExtra("role", etRole.text.toString())
            intent.putExtra("phone", etPhone.text.toString())
            startActivity(intent)
            finish()
        }
    }
}
