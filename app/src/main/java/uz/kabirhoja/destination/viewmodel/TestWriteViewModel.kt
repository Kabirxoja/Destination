package uz.kabirhoja.destination.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import uz.kabirhoja.destination.data.data.Vocabulary
import uz.kabirhoja.destination.data.repository.MainRepository
import kotlinx.coroutines.launch

class TestWriteViewModel(application: Application, private val repository: MainRepository) : AndroidViewModel(application) {

    private val _filteredWords = MutableLiveData<List<Vocabulary>>()
    val filteredWords: LiveData<List<Vocabulary>> get() = _filteredWords

    fun getFilteredWords(units: List<Int>, types: List<String>) {
        viewModelScope.launch {
            val words = repository.getFilteredWords(units, types)
            _filteredWords.postValue(words)
        }
    }


}