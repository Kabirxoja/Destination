package com.example.destination.ui.additions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.destination.R
import com.example.destination.databinding.FragmentBottomSheetLanguageBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BottomSheetLanguage : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetLanguageBinding? = null
    private val binding get() = _binding!!
    private var listener: BottomSheetLanguageListener? = null
    private var languageSelected = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetLanguageBinding.inflate(layoutInflater, container, false)

        binding.bottomSingleButton.setOnClickListener {
            Toast.makeText(binding.root.context, "wwwww", Toast.LENGTH_SHORT).show()
            listener?.onOkButtonClicked(languageSelected)
            dismiss()
        }

        binding.row1.setOnClickListener {
            languageSelected = "uz"
            updateRowBackgrounds(binding.text1, binding.text2)
        }

        binding.row2.setOnClickListener {
            languageSelected = "ka"
            updateRowBackgrounds(binding.text2, binding.text1)
        }

        return binding.root
    }
    private fun updateRowBackgrounds(selectedRow: TextView, unselectedRow: TextView) {
        selectedRow.setTextColor(resources.getColor(R.color.green))
        unselectedRow.setTextColor(resources.getColor(R.color.black))
    }

    interface BottomSheetLanguageListener {
        fun onOkButtonClicked(rowSelections: String)
    }
    fun setListener(listener: BottomSheetLanguageListener) {
        this.listener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}