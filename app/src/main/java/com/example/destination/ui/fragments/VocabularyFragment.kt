package com.example.destination.ui.fragments

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.destination.R
import com.example.destination.data.data.VocabularyItem
import com.example.destination.databinding.FragmentVocabularyBinding
import com.example.destination.ui.adapters.VocabularyAdapter
import com.example.destination.ui.adapters.WordPagerAdapter
import com.example.destination.viewmodel.VocabularyViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
        viewModel = ViewModelProvider(this)[VocabularyViewModel::class.java]
        tts = TextToSpeech(requireContext(), this)

        arguments?.let {
            topicNumber = it.getInt("topicUnit")
            viewModel.observeWordsByUnit(topicNumber.toString())
        }

        setupRecyclerView()
        setupViewPager()
        observeData()
        setupClickListeners()
        return binding.root
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
        }

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
    }

    override fun onNoteClick(vocabularyItem: VocabularyItem) {
        viewModel.updateItem(vocabularyItem.copy(isNoted = if (vocabularyItem.isNoted == 1) 0 else 1))
    }

    override fun onAudioClick(vocabularyItem: VocabularyItem) {
        val word = vocabularyItem.enWord
            .replace("sb", "somebody")
            .replace("sth", "something")
            .split(Regex("\\b(adj|v|adv|n)\\b"))
            .firstOrNull() ?: ""
        speakWord(word)
    }

    override fun onNoteClickPager(vocabularyItem: VocabularyItem) {
        onNoteClick(vocabularyItem)
        Toast.makeText(requireContext(), "Note status changed", Toast.LENGTH_SHORT).show()
    }

    override fun onAudioClickPager(vocabularyItem: VocabularyItem) {
        val word = vocabularyItem.enWord
            .replace("sb", "somebody")
            .replace("sth", "something")
            .split(Regex("\\b(adj|v|adv|n)\\b"))
            .firstOrNull() ?: ""
        speakWord(word)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) tts.language = Locale.US
    }

    private fun speakWord(word: String) {
        setTTSVoice("female")
        tts.apply {
            setSpeechRate(0.9f)
            setPitch(1.1f)
            speak(word, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    private fun setTTSVoice(gender: String) {
        tts.voices.find { voice ->
            when (gender) {
                "female" -> voice.name.contains("en-gb-x-gbc", ignoreCase = true)
                "male" -> voice.name.contains("en-us-x-tfb", ignoreCase = true)
                else -> false
            }
        }?.let { tts.voice = it }
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


