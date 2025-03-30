package com.example.destination.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.destination.data.local.AppDatabase
import com.example.destination.data.local.VocabularyEntity
import com.example.destination.data.repository.VocabularyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class VocabViewModel(application: Application) : AndroidViewModel(application) {
    private val database: AppDatabase = AppDatabase.getDatabase(application)
    private val repository = VocabularyRepository(database.vocabularyDao(), application)

    private val _wordsByUnit = MutableLiveData<List<VocabularyEntity>>()
    val wordsByUnit: LiveData<List<VocabularyEntity>> get() = _wordsByUnit

    fun loadData(fileName: String) {
        viewModelScope.launch {
            repository.loadJSONAndSaveToDatabase(fileName)
        }
    }

//    fun getWordsByUnit(unit: String) {
//        viewModelScope.launch {
//            _wordsByUnit.postValue(repository.getWordsByUnit(unit))
//        }
//    }

    fun getRowCount(callback: (Int) -> Unit) {
        viewModelScope.launch {
            val count = repository.getRowCount()
            callback(count)  // Return result via callback        }
        }
    }


}

