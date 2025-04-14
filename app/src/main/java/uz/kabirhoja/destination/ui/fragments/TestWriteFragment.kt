package uz.kabirhoja.destination.ui.fragments

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kabirhoja.destination.R
import uz.kabirhoja.destination.data.data.WordItem
import uz.kabirhoja.destination.data.data.OptionItem
import uz.kabirhoja.destination.data.data.TestChoiceItem
import uz.kabirhoja.destination.data.repository.MainViewModelFactory
import com.kabirhoja.destination.databinding.FragmentTestWriteBinding
import uz.kabirhoja.destination.custom.AnimationButton.animateClick
import uz.kabirhoja.destination.viewmodel.TestWriteViewModel

class TestWriteFragment : Fragment() {
    private var _binding: FragmentTestWriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TestWriteViewModel

    private var wordList = mutableListOf<WordItem>() // Uzbek, English, Status
    private var currentIndex = 0
    private var correctAnswersCount = 0
    private var totalWords = 0
    private var doNotKnow: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestWriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.doNotKnowTextView.setOnClickListener {
            revealAnswer()
            it.animateClick()
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
            it.animateClick()
        }

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(requireActivity().application)
        )[TestWriteViewModel::class.java]


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

        Log.d("errr", "unitTest: $selectedUnits, optionTest: $selectedOption")

        viewModel.getFilteredWords(selectedUnits, selectedOption)

        viewModel.filteredWords.observe(viewLifecycleOwner) { words ->
            wordList.clear()

            wordList.addAll(words.map {
                WordItem(
                    uzbekWord = it.translatedWord,
                    englishWord = it.englishWord.substringBefore(" ("),
                    type = it.type,
                    unit = it.unit,
                    definition = it.definition,
                    status = 0
                )
            }.sortedBy { it.unit.toIntOrNull() ?: 0 })

            totalWords = wordList.size
            if (wordList.isNotEmpty()) showWord()
        }


        binding.editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val word = binding.editText.text.toString().trim()

                if (word.isNotEmpty()) {
                    processWord(word)  // Process the word
                    binding.editText.setText("")  // Clear the EditText for new input
                }
                true
            } else {
                false
            }
        }
        setupCancelIconAndDoNotKnowText()
        setupCancelClickListener()

        binding.btnClue.setOnClickListener {
            binding.clueLayout.visibility = View.VISIBLE
            it.animateClick()
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupCancelIconAndDoNotKnowText() {
        val cancelIcon: Drawable? =
            AppCompatResources.getDrawable(binding.root.context, R.drawable.ic_cancel)

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.editText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        null, null, null, null
                    )
                    binding.doNotKnowTextView.visibility = View.VISIBLE
                } else {
                    binding.editText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        null, null, cancelIcon, null
                    )
                    binding.doNotKnowTextView.visibility = View.INVISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupCancelClickListener() {
        binding.editText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawables = binding.editText.compoundDrawablesRelative
                if (drawables[2] != null) {
                    if (event.rawX >= binding.editText.right - drawables[2].bounds.width()) {
                        binding.editText.text.clear()
                        return@setOnTouchListener true
                    }
                }
            }
            return@setOnTouchListener false
        }
    }

    private fun processWord(word: String) {
        val currentWord = wordList[currentIndex] // Get the current WordItem
        val correctAnswer = currentWord.englishWord // English word
        binding.txtEnglish.visibility = View.VISIBLE

        if (word.equals(correctAnswer, ignoreCase = true) && !doNotKnow) {
            correctAnswersCount++
            wordList[currentIndex] = currentWord.copy(status = 1) // Mark as correct
            binding.txtEnglish.setTextColor(resources.getColor(R.color.green))
            binding.imgCorrect.visibility = View.VISIBLE
            binding.imgIncorrect.visibility = View.GONE
        } else {
            if (word.equals(correctAnswer, ignoreCase = true)) {
                binding.imgCorrect.visibility = View.VISIBLE
                binding.imgIncorrect.visibility = View.GONE
            } else {
                binding.imgCorrect.visibility = View.GONE
                binding.imgIncorrect.visibility = View.VISIBLE
            }
            wordList[currentIndex] = currentWord.copy(status = -1) // Mark as incorrect
            binding.txtEnglish.setTextColor(resources.getColor(R.color.green))
        }

        currentIndex++ // Move to the next word
        Handler(Looper.getMainLooper()).postDelayed({ showWord() }, 1000)
    }

    private fun showWord() {
        Log.d("ShowWord", "Current Index: $currentIndex, Word List Size: ${wordList}")

        // Skip words already marked as correct (1)
        while (currentIndex < wordList.size && wordList[currentIndex].status == 1) {
            currentIndex++
        }

        // If we reach the end, check for incorrect (-1) words
        if (currentIndex >= wordList.size) {
            if (wordList.any { it.status == -1 }) {
                // Reset index to retry only incorrect words
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
                findNavController().navigate(R.id.action_testFragment_to_resultFragment, bundle)
            }
            return
        }

        // Show next word
        val currentWord = wordList[currentIndex]
        binding.txtTranslation.text = currentWord.uzbekWord
        binding.txtEnglish.text = currentWord.englishWord
        binding.unitText.text = "Unit №${currentWord.unit}"
        when (currentWord.type) {
            "topic_vocabulary" -> binding.unitType.text = "Topic vocabulary"
            "phrasal_verbs" -> binding.unitType.text = "Phrasal verbs"
            "prepositional_phrases" -> binding.unitType.text = "Prepositional phrases"
            "word_patterns" -> binding.unitType.text = "Word patterns"
            "word_formation" -> binding.unitType.text = "Word formation"
        }
        binding.txtEnglish.visibility = View.INVISIBLE
        binding.txtClue.text = currentWord.definition
        binding.clueLayout.visibility = View.GONE
        binding.imgCorrect.visibility = View.GONE
        binding.imgIncorrect.visibility = View.GONE
        binding.editText.text.clear()

        doNotKnow = false
    }


    private fun revealAnswer() {
        binding.imgCorrect.visibility = View.GONE
        binding.imgIncorrect.visibility = View.GONE
        binding.txtEnglish.visibility = View.VISIBLE
        val currentWord = wordList[currentIndex]

        doNotKnow = true

        if (currentWord.status == 0) { // Only update if unanswered
            wordList[currentIndex] = currentWord.copy(status = -1)
        }
    }
}
