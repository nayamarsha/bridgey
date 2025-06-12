package com.example.bridgey2.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bridgey2.R

class PostFragment : Fragment() {

    private lateinit var btnPost: Button
    private lateinit var btnCancel: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post, container, false)

        // Inisialisasi button
        btnPost = view.findViewById(R.id.postButton)
        val btnCancel = view.findViewById<View?>(R.id.headerCancel)

        // Event klik tombol POST
        btnPost.setOnClickListener {
            // Disini kamu bisa tambahkan logika posting data, misalnya validasi atau API call
            Toast.makeText(requireContext(), "Post Berhasil!", Toast.LENGTH_SHORT).show()

            // Setelah posting, kembalikan ke fragment sebelumnya atau fragment lain
            requireActivity().supportFragmentManager.popBackStack()
        }

        // Event klik tombol Cancel atau Next
        btnCancel?.setOnClickListener {
            // Kembali ke fragment sebelumnya atau fragment lain
            requireActivity().supportFragmentManager.popBackStack()
        }

        return view
    }
}