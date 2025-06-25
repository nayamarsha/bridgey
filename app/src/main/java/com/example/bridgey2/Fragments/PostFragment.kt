package com.example.bridgey2.Fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.bridgey2.R
import com.google.android.material.button.MaterialButton

class PostFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_post, container, false)

        view.findViewById<MaterialButton>(R.id.postButton).setOnClickListener {
            requestPermission()
        }

        return view
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 101)
        } else {
            chooseFileButton()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 101) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooseFileButton()
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun chooseFileButton() {
        val intent = Intent(Intent.ACTION_VIEW, MediaStore.Downloads.EXTERNAL_CONTENT_URI)
        intent.type = "*/*"
        startActivity(intent)
    }
}

