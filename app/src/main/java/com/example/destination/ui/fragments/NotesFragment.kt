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
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.destination.data.data.VocabularyItem
import com.example.destination.data.local.VocabularyEntity
import com.example.destination.data.repository.MainViewModelFactory
import com.example.destination.databinding.FragmentNotesBinding
import com.example.destination.ui.adapters.NoteAdapter
import com.example.destination.ui.adapters.SearchAdapter
import com.example.destination.viewmodel.NotesViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var searchAdapter: NoteAdapter

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

        notesViewModel = ViewModelProvider(this, MainViewModelFactory(requireActivity().application))[NotesViewModel::class.java]

        notesViewModel.getNotes()

        searchAdapter = NoteAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context)

        lifecycleScope.launch {
            notesViewModel.filteredWords.collectLatest { wordList ->
                Log.d("dddddd", wordList.toString())
                binding.recyclerView.adapter = searchAdapter
                searchAdapter.submitList(wordList)
            }
        }

    }



}