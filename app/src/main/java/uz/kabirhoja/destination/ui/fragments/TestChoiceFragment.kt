package uz.kabirhoja.destination.ui.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kabirhoja.destination.R

import uz.kabirhoja.destination.data.data.OptionItem
import uz.kabirhoja.destination.data.data.TestChoiceItem
import uz.kabirhoja.destination.data.data.WordItem
import uz.kabirhoja.destination.data.repository.MainViewModelFactory
import com.kabirhoja.destination.databinding.FragmentTestOptionBinding
import uz.kabirhoja.destination.custom.AnimationButton.animateClick
import uz.kabirhoja.destination.viewmodel.TestChoiceViewModel

class TestChoiceFragment : Fragment() {
    private var _binding: FragmentTestOptionBinding? = null
    private val binding get() = _binding!!
    private lateinit var testChoiceViewModel: TestChoiceViewModel

    private var wordList = mutableListOf<WordItem>() // Uzbek, English, Status
    private var currentIndex = 0
    private var options: List<String> = emptyList()
    private var totalWords = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTestOptionBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        testChoiceViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(requireActivity().application)
        )[TestChoiceViewModel::class.java]


        val unitTest = arguments?.getSerializable("updatedNumberList") as? ArrayList<TestChoiceItem>
        val optionTest = arguments?.getSerializable("updatedOptionList") as? ArrayList<OptionItem>

        Log.d("eyuu", "unitTest: $unitTest, optionTest: $optionTest")

        val selectedOption: List<String> = optionTest
            ?.filter { it.isChecked }
            ?.map { it.text }
            ?.dropLast(1) // remove the last item
            ?.map { it.lowercase().replace(" ", "_") }
            ?: emptyList()


        val selectedUnits: List<Int> = unitTest
            ?.filter { it.checked }
            ?.map { it.unitNumber }
            ?: emptyList()

        testChoiceViewModel.setOptions(selectedUnits, selectedOption)

        testChoiceViewModel.getOptions.observe(viewLifecycleOwner) { options ->
            Log.d("tuuya", "unitTest: $options")

            wordList.clear()
            wordList.addAll(options.map {
                WordItem(
                    uzbekWord = it.translatedWord,
                    englishWord = it.englishWord.substringBefore(" ("),
                    type = it.type,
                    unit = it.unit,
                    definition = it.definition,
                    status = 0
                )
            }.sortedBy { it.unit.toIntOrNull() ?: 0 })

            if (wordList.isNotEmpty())
                showWord()
        }


        binding.optionA.setOnClickListener {
            it.animateClick(scaleNormal = 1.0f, scaleShrink = 0.94f)
            checkWord(binding.optionA.text.toString())
        }
        binding.optionB.setOnClickListener {
            it.animateClick(scaleNormal = 1.0f, scaleShrink = 0.94f)
            checkWord(binding.optionB.text.toString())
        }
        binding.optionC.setOnClickListener {
            it.animateClick(scaleNormal = 1.0f, scaleShrink = 0.94f)
            checkWord(binding.optionC.text.toString())
        }
        binding.optionD.setOnClickListener {
            it.animateClick(scaleNormal = 1.0f, scaleShrink = 0.94f)
            checkWord(binding.optionD.text.toString())
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
            it.animateClick()
        }

        binding.btnClue.setOnClickListener {
            binding.clueLayout.visibility = View.VISIBLE
            it.animateClick()
        }

    }


    private fun showWord() {
        while (currentIndex < wordList.size && wordList[currentIndex].status == 1) {
            currentIndex++
        }

        if (currentIndex >= wordList.size) {
            if (wordList.any { it.status == -1 }) {
                Toast.makeText(
                    binding.root.context,
                    "Retrying incorrect words...",
                    Toast.LENGTH_SHORT
                ).show()
                currentIndex = 0
                showWord()
            } else {
                val bundle = Bundle()
                totalWords = wordList.size
                bundle.putInt("listSize", totalWords)
                findNavController().navigate(
                    R.id.action_testChoiceFragment_to_resultFragment,
                    bundle
                )
            }
            return
        }


        if (currentIndex < wordList.size) {
            val currentWord = wordList[currentIndex]
            options = generateOptions(currentWord)
            binding.txtTranslation.text = currentWord.uzbekWord
            binding.optionA.text = options.getOrNull(0) ?: ""
            binding.optionB.text = options.getOrNull(1) ?: ""
            binding.optionC.text = options.getOrNull(2) ?: ""
            binding.optionD.text = options.getOrNull(3) ?: ""

            binding.txtEnglish.visibility = View.INVISIBLE
            binding.imgCorrect.visibility = View.GONE
            binding.imgIncorrect.visibility = View.GONE
            binding.clueLayout.visibility = View.GONE


            binding.unitText.text = "Unit №${currentWord.unit}"
            when (currentWord.type) {
                "topic_vocabulary" -> binding.unitType.text = "Topic vocabulary"
                "phrasal_verbs" -> binding.unitType.text = "Phrasal verbs"
                "prepositional_phrases" -> binding.unitType.text = "Prepositional phrases"
                "word_patterns" -> binding.unitType.text = "Word patterns"
                "word_formation" -> binding.unitType.text = "Word formation"
            }

            binding.txtClue.text = currentWord.definition

            binding.optionA.isEnabled = true
            binding.optionB.isEnabled = true
            binding.optionC.isEnabled = true
            binding.optionD.isEnabled = true

            val defaultTextColor = ContextCompat.getColor(requireContext(), R.color.black)
            listOf(binding.optionA, binding.optionB, binding.optionC, binding.optionD).forEach {
                it.setTextColor(defaultTextColor)
                val color = ContextCompat.getColor(binding.root.context, R.color.white)
                val colorStateList = ColorStateList.valueOf(color)
                it.setBackgroundResource(R.drawable.bac_curve)

                ViewCompat.setBackgroundTintList(
                    it,
                    colorStateList
                )
            }
        }


    }

    private fun generateOptions(currentWord: WordItem): List<String> {
        if (currentWord == null) return emptyList()

        val incorrectOptions = mutableSetOf<String>()
        while (incorrectOptions.size < 3 && wordList.size > 1) {
            val randomWord = wordList.random()
            if (randomWord.englishWord != currentWord.englishWord) {
                incorrectOptions.add(randomWord.englishWord)
            }
        }

        val allOptions = incorrectOptions.toList() + currentWord.englishWord
        return allOptions.shuffled()
    }

    private fun checkWord(selectedText: String) {
        if (currentIndex < wordList.size) {
            val correctText = wordList[currentIndex].englishWord

            // Show the selected answer
            binding.txtEnglish.text = selectedText
            binding.txtEnglish.visibility = View.VISIBLE

            // Reset button colors
            val defaultColor = ContextCompat.getColor(requireContext(), android.R.color.transparent)
            listOf(binding.optionA, binding.optionB, binding.optionC, binding.optionD).forEach {
                it.setBackgroundColor(defaultColor)
            }

            // Show correct and incorrect feedback
            if (selectedText.equals(correctText, ignoreCase = true)) {
                wordList[currentIndex] = wordList[currentIndex].copy(status = 1)
                binding.imgCorrect.visibility = View.VISIBLE
                binding.imgIncorrect.visibility = View.GONE

                // Highlight correct in green
                highlightButtonWithText(correctText, R.color.green)
            } else {
                wordList[currentIndex] = wordList[currentIndex].copy(status = -1)
                binding.imgCorrect.visibility = View.GONE
                binding.imgIncorrect.visibility = View.VISIBLE

                // Highlight selected in red, correct in green
                highlightButtonWithText(selectedText, R.color.red)
                highlightButtonWithText(correctText, R.color.green)
            }

            // Disable all buttons
            listOf(binding.optionA, binding.optionB, binding.optionC, binding.optionD).forEach {
                it.isEnabled = false
            }

            currentIndex++
            Handler(Looper.getMainLooper()).postDelayed({ showWord() }, 1000)
        }
    }

    private fun highlightButtonWithText(text: String, colorResId: Int) {

        val color = ContextCompat.getColor(requireContext(), colorResId)
        when (text) {
            binding.optionA.text -> binding.optionA.setTextColor(color)
            binding.optionB.text -> binding.optionB.setTextColor(color)
            binding.optionC.text -> binding.optionC.setTextColor(color)
            binding.optionD.text -> binding.optionD.setTextColor(color)
        }

        listOf(binding.optionA, binding.optionB, binding.optionC, binding.optionD).forEach {
            it.setBackgroundResource(R.drawable.bac_curve)
        }
    }
}