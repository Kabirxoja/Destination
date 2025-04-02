package com.example.destination.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.destination.data.local.AppDatabase
import com.example.destination.data.local.VocabularyEntity
import com.example.destination.data.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MainRepository
    private val _filteredWords = MutableStateFlow<List<VocabularyEntity>>(emptyList())
    val filteredWords: StateFlow<List<VocabularyEntity>> get() = _filteredWords

    init {
        val dao = AppDatabase.getDatabase(application).vocabularyDao()
        repository = MainRepository(dao, application)

        viewModelScope.launch {
            repository.getNotedWords().collect { wordList ->
                _filteredWords.value = wordList.sortedBy { it.unit?.toInt()}
            }
        }
    }


}