package uz.kabirhoja.destination.ui.fragments

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import uz.kabirhoja.destination.data.data.UpdatedNotes
import uz.kabirhoja.destination.data.data.Vocabulary
import uz.kabirhoja.destination.data.repository.MainViewModelFactory
import com.kabirhoja.destination.databinding.FragmentNotesBinding
import uz.kabirhoja.destination.ui.adapters.NoteAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.kabirhoja.destination.ui.additions.MainSharedPreference
import uz.kabirhoja.destination.viewmodel.NotesViewModel
import java.util.Locale

class NotesFragment : Fragment(), NoteAdapter.OnNoteClickListener, TextToSpeech.OnInitListener {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var tts: TextToSpeech

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(requireActivity().application)
        )[NotesViewModel::class.java]

        notesViewModel.getNotes()

        noteAdapter = NoteAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        noteAdapter.setOnNoteClickListener(this)


        lifecycleScope.launch {
            notesViewModel.filteredWords.collectLatest { wordList ->
                Log.d("dddddd", wordList.toString())
                binding.recyclerView.adapter = noteAdapter
                noteAdapter.submitList(wordList)
            }
        }

        setTextSpeech()
    }

    override fun onNoteClick(vocabularyEntity: Vocabulary) {
        notesViewModel.updateItem(
            UpdatedNotes(
                vocabularyEntity.id,
                if (vocabularyEntity.isNoted == 1) 0 else 1
            )
        )
    }


    override fun onAudioClick(vocabularyEntity: Vocabulary) {
        speakWord(
            vocabularyEntity.englishWord.replace("sb", "somebody")
                .replace("sth", "something")
                .split(Regex("\\b(adj|v|adv|n)\\b"))
                .firstOrNull() ?: ""
        )
    }

    private fun setTextSpeech() {
        tts = TextToSpeech(binding.root.context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.language = Locale.ENGLISH
            } else {
                Toast.makeText(
                    binding.root.context,
                    "Speech voice initialization failed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
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
            tts.voice = voice
            tts.setSpeechRate(0.9f) // Adjust speech rate
            tts.setPitch(1.1f)      // Adjust pitch
            tts.speak(word, TextToSpeech.QUEUE_FLUSH, null, null)
        } else {
            println("Voice $voiceType not found. Available voices: ${availableVoices.map { it.name }}")
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS)
            tts.language = Locale.ENGLISH
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.stop()
        tts.shutdown()
    }


}