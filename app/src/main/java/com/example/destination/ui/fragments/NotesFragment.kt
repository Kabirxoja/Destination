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


        notesViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(requireActivity().application)
        )[NotesViewModel::class.java]

        searchAdapter = NoteAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(root.context)
//        searchAdapter.setOnNoteClickListener(this)

        lifecycleScope.launch {
            notesViewModel.filteredWords.collectLatest { wordList ->
                Log.d("dddddd", wordList.toString())
                val changedList = wordList.map { it.toParentItem() }
                binding.recyclerView.adapter = searchAdapter
                searchAdapter.submitList(changedList)
            }
        }



        return root
    }

    private fun VocabularyEntity.toParentItem() = VocabularyItem(
        unit = unit ?: "",
        type = type ?: "",
        enWord = englishWord ?: "",
        uzWord = uzbekWord ?: "",
        kaWord = karakalpakWord ?: "",
        definition = definition ?: "",
        enExample = exampleInEnglish ?: "",
        uzExample = exampleInUzbek ?: "",
        kaExample = exampleInKarakalpak ?: "",
        isNoted = isNoted ?: 0,
        id = id
    )


}