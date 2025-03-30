package com.example.destination.ui.fragments

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.destination.databinding.FragmentNotesBinding
import java.util.Locale

class NotesFragment : Fragment(), TextToSpeech.OnInitListener {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var tts: TextToSpeech
    private lateinit var editText: EditText
    private lateinit var btnSpeakMale: Button
    private lateinit var btnSpeakFemale: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        editText = binding.editTextWord
        btnSpeakMale = binding.btnSpeakMale
        btnSpeakFemale = binding.btnSpeakFemale

        tts = TextToSpeech(binding.root.context, this)

        btnSpeakMale.setOnClickListener { speakWord("male") }
        btnSpeakFemale.setOnClickListener { speakWord("female") }

        return root
    }




    private fun speakWord(gender: String) {
        val word = editText.text.toString().trim()
        if (word.isEmpty()) return

        setTTSVoice(gender)  // Set voice type (Male or Female)
        tts.setSpeechRate(0.9f)  // Slow down for better clarity
        tts.setPitch(1.1f)       // Adjust pitch for natural sound

        tts.speak(word, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun setTTSVoice(gender: String) {
        val availableVoices = tts.voices
        // Find a male or female voice based on voice attributes
        val selectedVoice = availableVoices.find { voice ->
            when (gender) {
                "female" -> voice.name.contains(
                    "en-gb-x-gbc",
                    ignoreCase = true
                ) // Some female voices
                "male" -> voice.name.contains("en-us-x-tfb", ignoreCase = true) // Some male voices
                else -> false
            }
        }


        if (selectedVoice != null) {
            tts.voice = selectedVoice
            for (voice in tts.voices) {
                Log.d("wwww", "Available voice: ${voice.name}")
            }
            Log.d("kkkkkk", "Voice set to: ${selectedVoice.name}")
        } else {
            Log.e("kkkkkk", "Requested $gender voice not found. Using default.")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        tts.stop()
        tts.shutdown()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.US  // Set language (adjust as needed)
        } else {
            Log.e("TTS", "Initialization failed")
        }
    }


}