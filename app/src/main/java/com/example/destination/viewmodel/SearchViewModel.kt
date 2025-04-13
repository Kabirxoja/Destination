package com.example.destination.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.destination.data.data.Vocabulary
import com.example.destination.data.local.AppDatabase
import com.example.destination.data.local.VocabularyEntity
import com.example.destination.data.repository.MainRepository
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MainRepository
    private val _filteredWords = MutableLiveData<List<Vocabulary>>()
    val filteredWords: LiveData<List<Vocabulary>> get() = _filteredWords

    init {
        val dao = AppDatabase.getDatabase(application).vocabularyDao()
        repository = MainRepository(dao, application)
    }

    fun searchItems(query: String) {
        viewModelScope.launch {
            val result = repository.getSearchItems(query)
            _filteredWords.postValue(result)
        }
    }

}