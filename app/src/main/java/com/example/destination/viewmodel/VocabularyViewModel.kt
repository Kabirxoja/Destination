package com.example.destination.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.destination.data.data.UpdatedNotes
import com.example.destination.data.data.Vocabulary
import com.example.destination.data.local.AppDatabase
import com.example.destination.data.local.VocabularyEntity
import com.example.destination.data.repository.MainRepository
import com.example.destination.data.data.VocabularyItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class VocabularyViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MainRepository

    private val _wordsByUnit = MutableStateFlow<List<Vocabulary>>(emptyList())
    val wordsByUnit: StateFlow<List<Vocabulary>> get() = _wordsByUnit

    init {
        val database = AppDatabase.getDatabase(application)
        repository = MainRepository(database.vocabularyDao(), application)
    }

    fun observeWordsByUnit(unit: String) {
        viewModelScope.launch {
            repository.getWordsByUnit(unit).map { it }.collect { wordsList ->
                _wordsByUnit.value = wordsList
            }
        }
    }

    fun updateItem(updatedNotes: UpdatedNotes) {
        viewModelScope.launch {
            repository.updateItem(updatedNotes)
        }
    }
}
