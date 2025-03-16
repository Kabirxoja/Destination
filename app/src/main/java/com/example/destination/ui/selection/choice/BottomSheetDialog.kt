package com.example.destination.ui.selection.choice

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.example.destination.R
import com.example.destination.databinding.FragmentBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    private var listener: BottomSheetListener? = null

    private val rowSelections = mutableMapOf<Int, Boolean>() // Store row selections
    private val buttonSelections = mutableMapOf<Int, Boolean>() // Store button selections
    private var selectedButton: Int = 0 // Track selected button

    fun setListener(listener: BottomSheetListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetDialogBinding.inflate(layoutInflater, container, false)

        // Initialize rowSelections with default values for all rows
        for (rowNumber in 1..5) {
            rowSelections[rowNumber] = true // Default value is false
        }

        // Set up row click listeners
        binding.row1.setOnClickListener {
            toggleRowSelection(1)
        }
        binding.row2.setOnClickListener {
            toggleRowSelection(2)
        }
        binding.row3.setOnClickListener {
            toggleRowSelection(3)
        }
        binding.row4.setOnClickListener {
            toggleRowSelection(4)
        }
        binding.row5.setOnClickListener {
            toggleRowSelection(5)
        }

        binding.button1.setOnClickListener {
            selectedButton = 1
            toggleButtonSelection(selectedButton, binding.button1, binding.button2)
        }
        binding.button2.setOnClickListener {
            selectedButton = 2
            toggleButtonSelection(selectedButton, binding.button2, binding.button1)
        }

        // Set up bottom button click listener
        binding.bottomSingleButton.setOnClickListener {
            if (selectedButton!=0){
                listener?.onOkButtonClicked(rowSelections, buttonSelections)
                Log.d("RowSelections", rowSelections.toString())
                dismiss()
            }
        }

        // Initialize images based on initial selections
        rowSelections.forEach { (rowNumber, isSelected) ->
            updateRowImage(rowNumber, isSelected)
        }

        return binding.root
    }

    private fun toggleRowSelection(rowNumber: Int) {
        val isSelected = !rowSelections.getOrDefault(rowNumber, true)
        rowSelections[rowNumber] = isSelected
        updateRowImage(rowNumber, isSelected)
    }

    private fun toggleButtonSelection(
        buttonNumber: Int,
        selectedButton: LinearLayout,
        otherButton: LinearLayout
    ) {
        val isClicked = !buttonSelections.getOrDefault(buttonNumber, true)
        buttonSelections[buttonNumber] = isClicked
        changeButtonTintColor(selectedButton, R.color.green)
        changeButtonTintColor(otherButton, R.color.light_gray)
    }

    private fun changeButtonTintColor(button: LinearLayout, colorRes: Int) {
        val color = ContextCompat.getColor(button.context, colorRes)
        val colorStateList = ColorStateList.valueOf(color)
        ViewCompat.setBackgroundTintList(button, colorStateList)
    }

    private fun updateRowImage(rowNumber: Int, isSelected: Boolean) {
        val imageView: ImageView? = when (rowNumber) {
            1 -> binding.icon1
            2 -> binding.icon2
            3 -> binding.icon3
            4 -> binding.icon4
            5 -> binding.icon5
            else -> null
        }

        imageView?.setImageDrawable(
            if (isSelected)
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_checkbox_true)
            else
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_checkbox_false)
        )
    }

    interface BottomSheetListener {
        fun onOkButtonClicked(rowSelections: Map<Int, Boolean>, buttonSelections: Map<Int, Boolean>)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}