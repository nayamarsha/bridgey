package com.example.bridgey2.Fragments

import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.bridgey2.Models.Post
import com.example.bridgey2.databinding.FragmentPostBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayOutputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class PostFragment : Fragment() {
    private var sImage: String? = ""
    private lateinit var db: DatabaseReference
    private lateinit var binding: FragmentPostBinding

    private val imageResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val uri = result.data?.data
            try {
                val inputStream = requireContext().contentResolver.openInputStream(uri!!)
                val myBitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()

                if (myBitmap != null) {
                    val stream = ByteArrayOutputStream()
                    myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    val bytes = stream.toByteArray()
                    sImage = Base64.encodeToString(bytes, Base64.DEFAULT)

                    binding.imageView.setImageBitmap(myBitmap)

                    Toast.makeText(requireContext(), "Image Selected", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to decode image.", Toast.LENGTH_SHORT).show()
                }
            } catch (ex: Exception) {
                Toast.makeText(requireContext(), "Error: ${ex.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostBinding.inflate(inflater, container, false)

        binding.chooseImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            imageResultLauncher.launch(intent)
        }

        binding.date.setOnClickListener {
            showDatePicker()
        }

        binding.postButton.setOnClickListener {
            insertData()
        }

        return binding.root
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
            binding.date.setText(formattedDate)
        }, year, month, day)

        datePicker.show()
    }

    private fun insertData() {
        val eventTitle = binding.title.text.toString().trim()
        val eventDesc = binding.description.text.toString().trim()
        val eventDate = binding.date.text.toString().trim()
        val eventLocation = binding.location.text.toString().trim()
        val eventCP = binding.cp.text.toString().trim()

        if (eventTitle.isEmpty() || eventDesc.isEmpty() || eventDate.isEmpty() ||
            eventLocation.isEmpty() || eventCP.isEmpty() || sImage.isNullOrEmpty()
        ) {
            Toast.makeText(requireContext(), "Please complete all fields and upload an image.", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isValidFutureDate(eventDate)) {
            Toast.makeText(
                requireContext(),
                "Tanggal harus dalam format dd/MM/yyyy dan minimal besok.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        db = FirebaseDatabase.getInstance().getReference("events")
        val id = db.push().key ?: return

        val event = Post(eventTitle, eventDesc, eventDate, eventLocation, eventCP, sImage)

        db.child(id).setValue(event).addOnSuccessListener {
            binding.title.text.clear()
            binding.description.text.clear()
            binding.date.text.clear()
            binding.location.text.clear()
            binding.cp.text.clear()
            binding.imageView.setImageDrawable(null)
            sImage = ""

            Toast.makeText(requireContext(), "Post Succeed", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Post Failed: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidFutureDate(dateStr: String): Boolean {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        sdf.isLenient = false

        return try {
            val inputDate = sdf.parse(dateStr)
            val today = Calendar.getInstance()
            today.set(Calendar.HOUR_OF_DAY, 0)
            today.set(Calendar.MINUTE, 0)
            today.set(Calendar.SECOND, 0)
            today.set(Calendar.MILLISECOND, 0)

            inputDate.after(today.time)
        } catch (e: ParseException) {
            false
        }
    }
}
