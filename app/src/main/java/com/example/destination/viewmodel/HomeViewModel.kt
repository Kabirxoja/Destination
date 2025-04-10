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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MainRepository

    init {
        val database = AppDatabase.getDatabase(application)
        repository = MainRepository(database.vocabularyDao(), application)
    }


    //ask for Sherali this place was written correctly
    fun setLoadJson() {
        viewModelScope.launch {
            repository.loadJSONAndSaveToDatabase()
        }
    }



}