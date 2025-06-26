package com.example.bridgey2.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts // Import ini
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

import com.example.bridgey2.R
import com.google.android.material.button.MaterialButton

class PostFragment : Fragment() {

    private val selectFileLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            Toast.makeText(requireContext(), "File dipilih: ${uri.lastPathSegment}", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireContext(), "Pemilihan file dibatalkan.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_post, container, false)

        view.findViewById<MaterialButton>(R.id.postButton).setOnClickListener {
            view.findNavController().navigate(R.id.action_postFragment_to_homeFragment)
        }

        view.findViewById<MaterialButton>(R.id.chooseFileButton).setOnClickListener {
            chooseFile()
        }

        return view
    }

    private fun chooseFile() {
        selectFileLauncher.launch("*/*")
    }
}
