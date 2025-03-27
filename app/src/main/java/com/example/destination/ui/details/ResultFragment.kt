package com.example.destination.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.destination.R
import com.example.destination.databinding.FragmentResultBinding


class ResultFragment : Fragment() {

    private var _viewBinding: FragmentResultBinding? = null
    private val binding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = FragmentResultBinding.inflate(inflater, container, false)

        val initialSize = arguments?.getInt("listSize") ?: 0

        binding.numberOfWords.text = initialSize.toString()

        binding.backToMenu.setOnClickListener{
            findNavController().popBackStack()
        }

        binding.startAgain.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_testFragment)
        }





        return binding.root
    }
}