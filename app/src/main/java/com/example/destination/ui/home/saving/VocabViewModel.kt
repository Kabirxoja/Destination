package com.example.destination.ui.home.saving

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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

    fun getWordsByUnit(unit: String) {
        viewModelScope.launch {
            _wordsByUnit.postValue(repository.getWordsByUnit(unit))
        }
    }


}

