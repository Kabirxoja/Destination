package com.example.destination.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.destination.R
import com.example.destination.databinding.FragmentTestChoiceBinding
import com.example.destination.ui.adapters.IndicatorAdapter
import com.example.destination.ui.additions.BottomSheetChoice
import com.example.destination.data.data.TestChoiceItem
import com.example.destination.viewmodel.TestChoiceViewModel

class TestChoiceFragment : Fragment(), BottomSheetChoice.BottomSheetListener {

    private var _binding: FragmentTestChoiceBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheetDialog: BottomSheetChoice

    private lateinit var testViewModel: TestChoiceViewModel

    private val selectedUnits = mutableListOf<Int>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        testViewModel = ViewModelProvider(this)[TestChoiceViewModel::class.java]

        _binding = FragmentTestChoiceBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val indicatorAdapter = IndicatorAdapter()
        binding.indicatorRv.adapter = indicatorAdapter
        binding.indicatorRv.layoutManager = GridLayoutManager(context, 4) // 3 columns

        testViewModel.numberList.observe(viewLifecycleOwner) {
            indicatorAdapter.updateList(it)
            Log.d("jjjjj", it.toString())
        }

        indicatorAdapter.setOnClickListener(object : IndicatorAdapter.OnClickItemListener {
            override fun onClickItem(item: TestChoiceItem) {
                if (item.checked) {
                    selectedUnits.add(item.unitNumber) // Add if checked
                } else {
                    selectedUnits.remove(item.unitNumber) // Remove if unchecked
                }
               if(selectedUnits.isEmpty()){
                   binding.startButton.isEnabled = false
                   binding.startButton.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.gray)
               }
                else
               {
                   binding.startButton.isEnabled = true
                   binding.startButton.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.blue)
               }
            }
        })

        binding.startButton.setOnClickListener {
            if (selectedUnits.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please select at least one unit",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                testViewModel.bottomSheetData.observe(viewLifecycleOwner) { data ->
                    val bundle = Bundle().apply {
                        putSerializable("rowSelections", HashMap(data.first))
                        putSerializable("buttonSelections", HashMap(data.second))
                        putIntegerArrayList("selectedUnits", ArrayList(selectedUnits))
                    }

                    findNavController().navigate(
                        R.id.action_navigation_test_choice_to_testFragment,
                        bundle
                    )
                }
            }
        }


        binding.adjustmentLayout.setOnClickListener {
            bottomSheetDialog = BottomSheetChoice()
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
        testViewModel.setBottomSheetData(rowSelections, buttonSelections)
    }

}