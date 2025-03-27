package com.example.destination.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.destination.data.local.AppDatabase
import com.example.destination.data.local.VocabularyEntity
import com.example.destination.data.repository.VocabularyRepository
import kotlinx.coroutines.launch
import java.util.ArrayList

class TestViewModel(application: Application) : AndroidViewModel(application) {


    private val repository: VocabularyRepository
    private val _filteredWords = MutableLiveData<List<VocabularyEntity>>()
    val filteredWords: LiveData<List<VocabularyEntity>> get() = _filteredWords

    init {
        val dao = AppDatabase.getDatabase(application).vocabularyDao()
        repository = VocabularyRepository(dao, application)
    }

    fun getFilteredWords(units: ArrayList<Int>?, types: List<String>) {
        viewModelScope.launch {
            val words = repository.getFilteredWords(units, types)
            _filteredWords.postValue(words)
        }
    }
}