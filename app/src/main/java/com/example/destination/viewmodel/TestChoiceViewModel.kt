package com.example.destination.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.destination.data.data.TestChoiceItem
import com.example.destination.data.data.Vocabulary
import com.example.destination.data.local.AppDatabase
import com.example.destination.data.repository.MainRepository
import kotlinx.coroutines.launch

class TestChoiceViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MainRepository
    private val getFilteredList = MutableLiveData<List<Vocabulary>>()
    val getOptions:LiveData<List<Vocabulary>> get() = getFilteredList


    init {
        val dao = AppDatabase.getDatabase(application).vocabularyDao()
        repository = MainRepository(dao, application)
    }

    fun setOptions(units: List<Int>, types: List<String>){
        viewModelScope.launch {
            val options = repository.getFilteredWords(units, types)
            getFilteredList.value = options
        }
    }


}