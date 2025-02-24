package com.example.destination.ui.search

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.destination.R
import com.example.destination.databinding.FragmentSearchBinding
import com.example.destination.ui.settings.SettingsViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textBanner
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        setupCancelIconVisibility()
        setupCancelClickListener()

        return root
    }

    private fun setupCancelIconVisibility() {
        val cancelIcon: Drawable? = AppCompatResources.getDrawable(binding.root.context, R.drawable.ic_cancel)

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.searchEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        null,
                        null,
                        null,
                        null
                    )
                } else {
                    binding.searchEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        null,
                        null,
                        cancelIcon,
                        null
                    )
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupCancelClickListener() {
        binding.searchEditText.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawables =  binding.searchEditText.compoundDrawablesRelative // Get the drawables

                if (drawables[2] != null) { // Check if the end drawable (cancel icon) is not null
                    if (event.rawX >=  binding.searchEditText.right - drawables[2].bounds.width()) {
                        binding.searchEditText.text.clear()
                        return@setOnTouchListener true
                    }
                }
            }
            return@setOnTouchListener false
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}