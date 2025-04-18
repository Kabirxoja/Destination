package uz.kabirhoja.destination.ui.additions

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import uz.kabirhoja.destination.data.data.SpeakerItem
import uz.kabirhoja.destination.ui.adapters.SpeakerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kabirhoja.destination.databinding.FragmentBottomSheetSpeakerBinding
import kotlinx.coroutines.launch
import java.util.ArrayList
import java.util.Locale

class BottomSheetSpeaker : BottomSheetDialogFragment(), SpeakerAdapter.OnClickItemListener,
    TextToSpeech.OnInitListener {


    private var _binding: FragmentBottomSheetSpeakerBinding? = null
    private val binding get() = _binding!!
    private var listener: BottomSheetSpeakerListener? = null
    private lateinit var adapter: SpeakerAdapter
    private var speakerSelected = ""
    private lateinit var tts: TextToSpeech
    private var list: List<SpeakerItem> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetSpeakerBinding.inflate(inflater, container, false)
        val root = binding.root


        binding.bottomSingleButton.setOnClickListener {
            listener?.onSelectedSpeaker(speakerSelected)
            dismiss()
        }


        adapter = SpeakerAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(root.context, 3)
        adapter.setOnClickListener(this)



        tts = TextToSpeech(requireContext()) { status ->
            if (status == TextToSpeech.SUCCESS) {
                lifecycleScope.launch {
                    list = speakWord()
                    adapter.updateList(list)
                }
            } else {
                Log.e("TTS", "TTS initialization failed.")
            }
        }





        return root

    }


    private fun speakWord(): List<SpeakerItem> {

        val availableVoices = tts.voices // Now safe to access

        val allowedLocales = listOf("en_US", "en_GB", "en_AU")
        val filteredVoices = availableVoices.filter { voice ->
            allowedLocales.contains(voice.locale.toString()) && !voice.isNetworkConnectionRequired
        }

//        filteredVoices.filter { it.quality >= 100 && it.latency <= 500 } // Adjust quality and latency for older APIs


//        val bestVoices = when {
//            Build.VERSION.SDK_INT in 21..27 -> {
//                filteredVoices.filter { it.quality >= 100 && it.latency <= 500 } // Adjust quality and latency for older APIs
//            }
//            Build.VERSION.SDK_INT >= 28 -> {
//                filteredVoices.filter { it.quality >= 200 && it.latency <= 300 } // Your original filter for newer APIs
//            }
//            else -> {
//                filteredVoices.filter { it.quality >= 100 && it.latency <= 500 } // Default for older than API 21, adjust as needed
//            }
//        }
        val list = mutableListOf<SpeakerItem>()

        for (i in filteredVoices.indices) {
            list.add(
                SpeakerItem(
                    i,
                    filteredVoices[i].name,
                    filteredVoices[i].locale.toString(),
                    filteredVoices[i].quality,
                    filteredVoices[i].latency,
                    filteredVoices[i].isNetworkConnectionRequired
                )
            )
        }

        return list
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


    interface BottomSheetSpeakerListener {
        fun onSelectedSpeaker(selectedSpeaker: String)
    }

    fun setListener(listener: BottomSheetSpeakerListener) {
        this.listener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickItem(item: SpeakerItem) {
        speakerSelected = item.name
        Log.d("ppppp", speakerSelected)
        playSpeaker(item.name)
    }

    private fun playSpeaker(speakerType: String) {
        val availableVoices = tts.voices
        val voice = availableVoices.find { it.name == speakerType }
        for (voice in tts!!.voices) {
            Log.d("gggg", voice.locale.language)
            Log.d("gggg", voice.locale.country)
            Log.d("gggg", voice.locale.displayName)

        }
        val word = "This data suggests that higher education remains a priority for many students"

        if (voice != null) {
            tts!!.voice = voice
            tts!!.setSpeechRate(0.9f) // Adjust speech rate
            tts!!.setPitch(1.1f)      // Adjust pitch
            tts!!.speak(word, TextToSpeech.QUEUE_FLUSH, null, null)
        } else {
            println("Voice $speakerType not found. Available voices: ${availableVoices.map { it.name }}")
        }

    }


}