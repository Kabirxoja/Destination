package com.example.destination.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.destination.databinding.FragmentSettingsBinding
import com.example.destination.ui.utils.applyPressEffect
import com.example.destination.viewmodel.SettingsViewModel

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    private val binding get() = _binding!!
    private var hasExited = false


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textBanner
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.customButton.setOnClickListener {
            Toast.makeText(binding.root.context, "pressed", Toast.LENGTH_SHORT).show()
        }

        binding.customButton.applyPressEffect(binding.movableContent,binding.buttonIcon, binding.buttonText) // ✅ Button will have the press effect

        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}