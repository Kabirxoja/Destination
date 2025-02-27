package com.example.destination.ui.test

import android.R
import android.annotation.SuppressLint
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
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.example.destination.databinding.FragmentBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


open class BottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    private var listener: BottomSheetListener? = null

    private val rowSelections = mutableMapOf<Int, Boolean>() // Store row selections
    private val buttonSelections = mutableMapOf<Int, Boolean>() // Store button selections
    private var selectedButton: Int? = null // Track selected button


    fun setListener(listener: BottomSheetListener) {
        this.listener = listener
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetDialogBinding.inflate(layoutInflater, container, false)

        binding.row1.setOnClickListener {
            val isSelected = !rowSelections.getOrDefault(1, false)
            rowSelections[1] = isSelected
            updateRowImage(1, isSelected)
        }
        binding.row2.setOnClickListener {
            val isSelected = !rowSelections.getOrDefault(2, false)
            rowSelections[2] = isSelected
            updateRowImage(2, isSelected)
        }
        binding.row3.setOnClickListener {
            val isSelected = !rowSelections.getOrDefault(3, false)
            rowSelections[3] = isSelected
            updateRowImage(3, isSelected)
        }
        binding.row4.setOnClickListener {
            val isSelected = !rowSelections.getOrDefault(4, false)
            rowSelections[4] = isSelected
            updateRowImage(4, isSelected)

        }
        binding.row5.setOnClickListener {
            val isSelected = !rowSelections.getOrDefault(5, false)
            rowSelections[5] = isSelected
            updateRowImage(5, isSelected)

        }


        binding.button1.setOnClickListener {
            val isClicked = !buttonSelections.getOrDefault(1, false)
            buttonSelections[1] = isClicked
            changeButtonTintColor(binding.button1, R.color.holo_green_dark)
            changeButtonTintColor(binding.button2, R.color.darker_gray)

        }
        binding.button2.setOnClickListener {
            val isClicked = !buttonSelections.getOrDefault(2, false)
            buttonSelections[2] = isClicked
            changeButtonTintColor(binding.button1, R.color.darker_gray)
            changeButtonTintColor(binding.button2, R.color.holo_green_dark)
        }


        binding.bottomSingleButton.setOnClickListener {
            listener?.onOkButtonClicked(rowSelections, buttonSelections)
            Log.d("sdfsf", rowSelections.toString())
            dismiss()
        }


        // Initialize images based on initial selections
        rowSelections.forEach { (rowNumber, isSelected) ->
            updateRowImage(rowNumber, isSelected)
        }



        return binding.root

    }

    private fun changeButtonTintColor(button: Button, colorRes: Int) {
        val color = ContextCompat.getColor(button.context, colorRes)
        val colorStateList = ColorStateList.valueOf(color)
        ViewCompat.setBackgroundTintList(button, colorStateList)
        //Or, if you don't need backwards compatibility:
        //button.backgroundTintList = colorStateList
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

        binding.icon1.setImageResource(R.drawable.ic_dialog_alert)

        imageView?.setImageDrawable(
            if (isSelected)
                ContextCompat.getDrawable(requireContext(), R.drawable.checkbox_on_background)
            else
                ContextCompat.getDrawable(requireContext(), R.drawable.checkbox_off_background)
        )


    }


    interface BottomSheetListener {
        fun onOkButtonClicked(
            rowSelections: Map<Int, Boolean>,
            buttonSelections: Map<Int, Boolean>
        )
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