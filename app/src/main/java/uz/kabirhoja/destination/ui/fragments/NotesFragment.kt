package uz.kabirhoja.destination.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import uz.kabirhoja.destination.viewmodel.NotesViewModel

class NotesFragment : Fragment(), NoteAdapter.OnNoteClickListener {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var noteAdapter: NoteAdapter

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

    }

    override fun onAudioClick(vocabularyEntity: Vocabulary) {

    }

    override fun onNoteClick(vocabularyEntity: Vocabulary) {
       notesViewModel.updateItem(UpdatedNotes(vocabularyEntity.id, if (vocabularyEntity.isNoted == 1) 0 else 1))
    }


}