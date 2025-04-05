package com.example.destination.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.destination.data.repository.MainViewModelFactory
import com.example.destination.databinding.FragmentSettingsBinding
import com.example.destination.ui.additions.BottomSheetLanguage
import com.example.destination.ui.additions.BottomSheetSpeaker
import com.example.destination.ui.additions.MainSharedPreference
import com.example.destination.viewmodel.SettingsViewModel

class SettingsFragment : Fragment(),
    BottomSheetLanguage.BottomSheetLanguageListener,
    BottomSheetSpeaker.BottomSheetSpeakerListener {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var bottomSheetLanguage: BottomSheetLanguage
    private lateinit var bottomSheetSpeaker: BottomSheetSpeaker


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        settingsViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(requireActivity().application)
        )[SettingsViewModel::class.java]


        val selectedLanguage = MainSharedPreference.getLanguage(requireContext())
        val selectedSpeaker = MainSharedPreference.getSpeakerType(requireContext())
        Log.d("SettingsFragment", "Selected Language: $selectedLanguage")
        Log.d("SettingsFragment", "Selected Speaker: $selectedSpeaker")

        // Observe language changes
        settingsViewModel.currentLanguage.observe(viewLifecycleOwner) { language ->
            binding.txtLanguageChose.text = language
        }

        // Observe speaker changes
        settingsViewModel.currentSpeaker.observe(viewLifecycleOwner) { speaker ->
            binding.txtVoiceChose.text = when {
                speaker.contains("gb") -> "UK"
                speaker.contains("au") -> "AUS"
                speaker.contains("us") -> "USA"
                else -> speaker
            }
        }





        binding.layoutLanguage.setOnClickListener {
            bottomSheetLanguage = BottomSheetLanguage()
            bottomSheetLanguage.setListener(this)
            bottomSheetLanguage.show(childFragmentManager, "BottomSheet")
        }

        binding.layoutVoice.setOnClickListener {
            bottomSheetSpeaker = BottomSheetSpeaker()
            bottomSheetSpeaker.setListener(this)
            bottomSheetSpeaker.show(childFragmentManager, "BottomSheet")
        }

        return root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSelectedLanguage(selectedLanguage: String) {
        settingsViewModel.updateLanguage(selectedLanguage)

    }

    override fun onSelectedSpeaker(selectedSpeaker: String) {
        settingsViewModel.updateSpeaker(selectedSpeaker)
    }


}