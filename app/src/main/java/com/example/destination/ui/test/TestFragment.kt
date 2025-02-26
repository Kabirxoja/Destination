package com.example.destination.ui.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.destination.databinding.FragmentTestBinding

class TestFragment : Fragment(){

    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val testViewModel = ViewModelProvider(this)[TestViewModel::class.java]

        _binding = FragmentTestBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textBanner

        val indicatorAdapter = IndicatorAdapter()
        binding.indicatorRv.adapter = indicatorAdapter
        binding.indicatorRv.layoutManager = GridLayoutManager(context, 4) // 3 columns

        testViewModel.numberList.observe(viewLifecycleOwner){
            indicatorAdapter.updateList(it)
        }

        indicatorAdapter.setOnClickListener(object : IndicatorAdapter.OnClickItemListener {
            override fun onClickItem(item: Int) {
                Toast.makeText(binding.root.context, "$item", Toast.LENGTH_SHORT).show()
            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}