package com.example.destination.ui.selection.test

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.destination.R
import com.example.destination.databinding.FragmentTestBinding


class TestFragment : Fragment() {
    private var _binding:  FragmentTestBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        binding.doNotKnowTextView.setOnClickListener{
            Log.d("ddddddd","gggggggggg")
        }

        val rowSelections = arguments?.getSerializable("rowSelections") as? HashMap<Int, Boolean> ?: hashMapOf()
        val buttonSelections = arguments?.getSerializable("buttonSelections") as? HashMap<Int, Boolean> ?: hashMapOf()
        Log.d("ooooo",rowSelections.toString()+""+buttonSelections.toString())

        setupCancelIconAndDoNotKnowText()


        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupCancelIconAndDoNotKnowText() {
        val cancelIcon: Drawable? = AppCompatResources.getDrawable(binding.root.context, R.drawable.ic_cancel)

        binding.putInto.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s.isNullOrEmpty()) {
                    // If the text is empty, show the "Do not know" TextView and hide the cancel icon
                    binding.putInto.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        null,
                        null,
                        null,
                        null
                    )
                    binding.doNotKnowTextView.visibility = View.VISIBLE
                } else {
                    // If the user types something, hide the "Do not know" TextView and show the cancel icon
                    binding.putInto.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        null,
                        null,
                        cancelIcon,
                        null
                    )
                    binding.doNotKnowTextView.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.putInto.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val word = binding.putInto.text.toString().trim()

                if (word.isNotEmpty()) {
                    processWord(word)  // Process the word
                    binding.putInto.setText("")  // Clear the EditText for new input
                }
                true
            } else {
                false
            }
        }

    }

    private fun processWord(word: String) {
        Toast.makeText(binding.root.context, "Word Entered: $word", Toast.LENGTH_SHORT).show()

    }


}