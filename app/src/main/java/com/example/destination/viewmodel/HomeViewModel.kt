package com.example.destination.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.destination.data.local.AppDatabase
import com.example.destination.data.local.VocabularyEntity
import com.example.destination.data.repository.MainRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val database: AppDatabase = AppDatabase.getDatabase(application)
    private val repository = MainRepository(database.vocabularyDao(), application)

    private val _wordsByUnit = MutableLiveData<List<VocabularyEntity>>()
    val wordsByUnit: LiveData<List<VocabularyEntity>> get() = _wordsByUnit

    init {
        viewModelScope.launch {
            repository.loadJSONAndSaveToDatabase()
        }
    }



    fun getRowCount(callback: (Int) -> Unit) {
        viewModelScope.launch {
            val count = repository.getRowCount()
            callback(count)
        }
    }


}

