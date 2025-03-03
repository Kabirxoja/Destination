package com.example.destination.ui.selection.choice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.destination.R
import com.example.destination.databinding.FragmentTestChoiceBinding
import com.example.destination.ui.selection.test.TestFragment

class TestChoiceFragment : Fragment(), BottomSheetDialog.BottomSheetListener {

    private var _binding: FragmentTestChoiceBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheetDialog: BottomSheetDialog

    private lateinit var testViewModel: TestChoiceViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        testViewModel = ViewModelProvider(this)[TestChoiceViewModel::class.java]

        _binding = FragmentTestChoiceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textBanner

        val indicatorAdapter = IndicatorAdapter()
        binding.indicatorRv.adapter = indicatorAdapter
        binding.indicatorRv.layoutManager = GridLayoutManager(context, 4) // 3 columns

        testViewModel.numberList.observe(viewLifecycleOwner){
            indicatorAdapter.updateList(it)
        }

        indicatorAdapter.setOnClickListener(object : IndicatorAdapter.OnClickItemListener {
            override fun onClickItem(item: TestChoiceItem) {
                Toast.makeText(binding.root.context, "$item", Toast.LENGTH_SHORT).show()
            }
        })

        binding.startButton.setOnClickListener{
            testViewModel.bottomSheetData.observe(viewLifecycleOwner) {
                Log.d("myDataaa",it.toString())
                val bundle = Bundle()
                bundle.putSerializable("rowSelections", HashMap(it.first)) // Convert Map to HashMap
                bundle.putSerializable("buttonSelections", HashMap(it.second))


                findNavController().navigate(R.id.action_navigation_test_choice_to_testFragment, bundle)
            }
        }



        binding.adjustmentButton.setOnClickListener {
            bottomSheetDialog = BottomSheetDialog()
            bottomSheetDialog.setListener(this) // Set the listener
            bottomSheetDialog.show(childFragmentManager, "BottomSheet")
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOkButtonClicked(
        rowSelections: Map<Int, Boolean>,
        buttonSelections: Map<Int, Boolean>
    ) {
        // Handle data from OKEY button click
        Log.d("tertete", "Row selections: $rowSelections")
        Log.d("tertete", "Button selections: $buttonSelections")
        // Transfer data to other fragments or ViewModel

        testViewModel.setBottomSheetData(rowSelections, buttonSelections)

    }

}