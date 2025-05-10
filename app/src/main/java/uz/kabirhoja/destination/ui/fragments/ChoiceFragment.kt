package uz.kabirhoja.destination.ui.fragments

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
import com.kabirhoja.destination.R
import uz.kabirhoja.destination.ui.adapters.IndicatorAdapter
import uz.kabirhoja.destination.data.data.TestChoiceItem
import uz.kabirhoja.destination.data.repository.MainViewModelFactory
import uz.kabirhoja.destination.data.data.OptionItem
import com.kabirhoja.destination.databinding.FragmentChoiceBinding
import uz.kabirhoja.destination.custom.AnimationButton
import uz.kabirhoja.destination.custom.AnimationButton.animateClick
import uz.kabirhoja.destination.viewmodel.ChoiceViewModel

class ChoiceFragment : Fragment(), IndicatorAdapter.OnClickItemListener {

    private var _binding: FragmentChoiceBinding? = null
    private val binding get() = _binding!!
    private lateinit var testViewModel: ChoiceViewModel
    private lateinit var indicatorAdapter: IndicatorAdapter
    private val selectedUnits = mutableListOf<Int>()


    private val options = mutableListOf(
        OptionItem("Topic vocabulary", true),
        OptionItem("Phrasal verbs", true),
        OptionItem("Prepositional phrases", true),
        OptionItem("Word patterns", true),
        OptionItem("Word formation", true),
        OptionItem("Test", false),
        OptionItem("Write", false)
    )

    private val units = listOf(
        TestChoiceItem("Fun and games", 3, false),
        TestChoiceItem("Learning and doing", 6, false),
        TestChoiceItem("Coming and going", 9, false),
        TestChoiceItem("Friends and relations", 12, false),
        TestChoiceItem("Buying and selling", 15, false),
        TestChoiceItem("Inventions and discoveries", 18, false),
        TestChoiceItem("Sending and receiving", 21, false),
        TestChoiceItem("People and daily life", 24, false),
        TestChoiceItem("Working and earning", 27, false),
        TestChoiceItem("Body and lifestyle", 30, false),
        TestChoiceItem("Creating and building", 33, false),
        TestChoiceItem("Nature and the universe", 36, false),
        TestChoiceItem("Laughing and crying", 39, false),
        TestChoiceItem("Problems and solutions", 42, false)
    )

    private var selectedOption: String? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChoiceBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        testViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(requireActivity().application)
        )[ChoiceViewModel::class.java]

        indicatorAdapter = IndicatorAdapter()
        binding.indicatorRv.adapter = indicatorAdapter
        binding.indicatorRv.layoutManager = GridLayoutManager(context, 4)
        indicatorAdapter.setOnClickListener(this)

        testViewModel.numberList.observe(viewLifecycleOwner) {
            indicatorAdapter.updateList(it)
        }

        testViewModel.setOptionList(options)
        testViewModel.setNumberList(units)


        binding.startButton.setOnClickListener {
            startExam()
            it.animateClick()
        }

        binding.adjustmentLayout.setOnClickListener {
            binding.additionalOptionsLayout.visibility = View.VISIBLE
            it.animateClick()
        }

        binding.text1.text = "Topic vocabulary"
        binding.text2.text = "Phrasal verbs"
        binding.text3.text = "Prepositional phrases"
        binding.text4.text = "Word patterns"
        binding.text5.text = "Word formation"


        binding.button1.setOnClickListener {
            if (selectedOption != "Test") {
                selectedOption?.let {
                    if (it == "Write") {
                        options[6].isChecked = false
                        binding.imgWrite.setImageResource(R.drawable.ic_pencil_off)
                        binding.txtWrite.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.black
                            )
                        )
                        binding.txtTest.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.green
                            )
                        )
                    }
                }

                // Select "Test"
                selectedOption = "Test"
                options[5].isChecked = true
                binding.imgTest.setImageResource(R.drawable.ic_option_on)
                binding.txtWrite.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                binding.txtTest.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.green
                    )
                )
            }
            it.animateClick()
        }

        binding.button2.setOnClickListener {
            if (selectedOption != "Write") {
                selectedOption?.let {
                    if (it == "Test") {
                        options[5].isChecked = false
                        binding.imgTest.setImageResource(R.drawable.ic_option_off)
                        binding.txtWrite.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.green
                            )
                        )
                        binding.txtTest.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.black
                            )
                        )
                    }
                }

                // Select "Write"
                selectedOption = "Write"
                options[6].isChecked = true
                binding.imgWrite.setImageResource(R.drawable.ic_pencil_on)
                binding.txtWrite.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.green
                    )
                )
                binding.txtTest.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
            }
            it.animateClick()
        }





        binding.row1.setOnClickListener {
            val item = options[0]
            item.isChecked = !item.isChecked
            binding.icon1.setImageResource(
                if (item.isChecked) R.drawable.ic_checkbox_true else R.drawable.ic_unselected
            )

        }

        binding.row2.setOnClickListener {
            val item = options[1]
            item.isChecked = !item.isChecked
            binding.icon2.setImageResource(
                if (item.isChecked) R.drawable.ic_checkbox_true else R.drawable.ic_unselected
            )
        }

        binding.row3.setOnClickListener {
            val item = options[2]
            item.isChecked = !item.isChecked
            binding.icon3.setImageResource(
                if (item.isChecked) R.drawable.ic_checkbox_true else R.drawable.ic_unselected
            )
        }

        binding.row4.setOnClickListener {
            val item = options[3]
            item.isChecked = !item.isChecked
            binding.icon4.setImageResource(
                if (item.isChecked) R.drawable.ic_checkbox_true else R.drawable.ic_unselected
            )
        }

        binding.row5.setOnClickListener {
            val item = options[4]
            item.isChecked = !item.isChecked
            binding.icon5.setImageResource(
                if (item.isChecked) R.drawable.ic_checkbox_true else R.drawable.ic_unselected
            )
        }


    }

    override fun onClickItem(item: TestChoiceItem) {
        if (item.checked) {
            selectedUnits.add(item.unitNumber) // Add if checked
        } else {
            selectedUnits.remove(item.unitNumber) // Remove if unchecked
        }
        if (selectedUnits.isEmpty()) {
            binding.startButton.isEnabled = false
            binding.startButton.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.gray)
        } else {
            binding.startButton.isEnabled = true
            binding.startButton.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.blue)
        }
    }

    private fun startExam() {
        Log.d("sssss", "coming data: $options")
        if (options[5].isChecked || options[6].isChecked) {
            if (selectedUnits.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please select at least one unit",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                var writeFragment = false
                val bundle = Bundle()

                testViewModel.optionList.observe(viewLifecycleOwner) { updatedOptionList ->
                    Log.d("OtherFragment", "Updated optionList in ViewModel: $updatedOptionList")
                    writeFragment = !updatedOptionList[5].isChecked

                    bundle.apply {
                        putSerializable("updatedOptionList", ArrayList(updatedOptionList))
                    }

                }

                testViewModel.numberList.observe(viewLifecycleOwner) { updatedNumberList ->
                    Log.d("OtherFragment", "Updated numberList in ViewModel: $updatedNumberList")

                    bundle.apply {
                        putSerializable("updatedNumberList", ArrayList(updatedNumberList))
                    }
                }

                if (writeFragment){
                    findNavController().navigate(
                        R.id.action_navigation_test_choice_to_testFragment,
                        bundle
                    )
                }else{
                    findNavController().navigate(
                        R.id.action_navigation_test_choice_to_testChoiceFragment,
                        bundle
                    )
                }


            }
        } else {
            Toast.makeText(
                requireContext(),
                "first of all, you should select options",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}


