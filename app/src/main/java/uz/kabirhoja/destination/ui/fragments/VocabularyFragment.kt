package uz.kabirhoja.destination.ui.fragments

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kabirhoja.destination.R
import uz.kabirhoja.destination.data.data.UpdatedNotes
import uz.kabirhoja.destination.data.data.Vocabulary
import uz.kabirhoja.destination.data.repository.MainViewModelFactory
import com.kabirhoja.destination.databinding.FragmentVocabularyBinding
import uz.kabirhoja.destination.ui.adapters.VocabularyAdapter
import uz.kabirhoja.destination.ui.adapters.WordPagerAdapter
import uz.kabirhoja.destination.ui.additions.MainSharedPreference
import uz.kabirhoja.destination.viewmodel.VocabularyViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.kabirhoja.destination.custom.AnimationButton.animateClick
import java.util.Locale
import kotlin.math.abs


class VocabularyFragment : Fragment(), VocabularyAdapter.OnNoteClickListener,
    WordPagerAdapter.OnItemClickListener, TextToSpeech.OnInitListener {

    private var _binding: FragmentVocabularyBinding? = null
    private val binding get() = _binding!!
    private lateinit var tts: TextToSpeech
    private lateinit var viewModel: VocabularyViewModel
    private var topicNumber: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVocabularyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(requireActivity().application)
        )[VocabularyViewModel::class.java]


        arguments?.let {
            topicNumber = it.getInt("topicUnit")
            viewModel.observeWordsByUnit(topicNumber.toString())
        }
        binding.txtUnit.text = "Unit " + topicNumber.toString()

        setupRecyclerView()
        setupViewPager()
        observeData()
        setupClickListeners()
        setTextSpeech()
    }

    private fun setupRecyclerView() {
        binding.mainRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = VocabularyAdapter().apply { setOnNoteClickListener(this@VocabularyFragment) }
        }
        binding.viewPager.apply {
            adapter = WordPagerAdapter().apply { setOnItemClickListener(this@VocabularyFragment) }
        }

    }

    private fun setupViewPager() {
        binding.viewPager.adapter =
            WordPagerAdapter().apply { setOnItemClickListener(this@VocabularyFragment) }
        binding.viewPager.setPageTransformer(false) { page, position ->
            val scale = if (position in -1.0..1.0) 1 - abs(position) * 0.25f else 0.75f
            page.apply {
                scaleX = scale
                scaleY = scale
                translationX = -position * width * 0.166f
            }
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            setupRecyclerView()
            viewModel.wordsByUnit.collectLatest { words ->
                (binding.mainRecyclerView.adapter as? VocabularyAdapter)?.submitList(words)
                (binding.viewPager.adapter as? WordPagerAdapter)?.getList(words)
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnChange.setOnClickListener {
            val isListView = binding.viewPager.visibility == View.GONE
            binding.viewPager.visibility = if (isListView) View.VISIBLE else View.GONE
            binding.mainRecyclerView.visibility = if (isListView) View.GONE else View.VISIBLE
            binding.btnChangeIcon.setBackgroundResource(if (isListView) R.drawable.ic_list else R.drawable.ic_card)
            observeData()
            it.animateClick()
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
            it.animateClick()
        }
    }

    override fun onNoteClick(vocabulary: Vocabulary) {
        viewModel.updateItem(
            UpdatedNotes(
                id = vocabulary.id,
                isNoted = if (vocabulary.isNoted == 1) 0 else 1
            )
        )
    }

    override fun onAudioClick(vocabulary: Vocabulary) {
        val word = vocabulary.englishWord
            .replace("sb", "somebody")
            .replace("sth", "something")
            .split(Regex("\\b(adj|v|adv|n)\\b"))
            .firstOrNull() ?: ""
        speakWord(word)
    }

    private fun setTextSpeech() {
        tts = TextToSpeech(binding.root.context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.language = Locale.ENGLISH
                println("TTS initialized successfully!")
            } else {
                println("TTS initialization failed.")
            }
        }
    }

    override fun onNoteClickPager(vocabulary: Vocabulary) {
        onNoteClick(vocabulary)
    }

    override fun onAudioClickPager(vocabulary: Vocabulary) {
        val word = (vocabulary.englishWord
            .replace("sb", "somebody")
            .replace("sth", "something")
            .split(Regex("\\b(adj|v|adv|n)\\b"))
            .firstOrNull() ?: "").also {
            speakWord(it)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS)
            tts.language = Locale.ENGLISH
    }

    private fun speakWord(word: String) {
        val speakerType = MainSharedPreference.getSpeakerType(binding.root.context)
        if (speakerType.isEmpty()) {
            Toast.makeText(binding.root.context, "Speaker type not selected", Toast.LENGTH_SHORT).show()
        } else {
            speakWordWithType(word, speakerType)
        }
    }

    private fun speakWordWithType(word: String, voiceType: String) {
        if (tts == null) {
            println("TTS is not initialized.")
            return
        }

        val availableVoices = tts.voices

        val voice = availableVoices.find { it.name == voiceType }

        if (voice != null) {
            tts!!.voice = voice
            tts!!.setSpeechRate(0.9f) // Adjust speech rate
            tts!!.setPitch(1.1f)      // Adjust pitch
            tts!!.speak(word, TextToSpeech.QUEUE_FLUSH, null, null)
        } else {
            println("Voice $voiceType not found. Available voices: ${availableVoices.map { it.name }}")
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.stop()
        tts.shutdown()
    }
}


