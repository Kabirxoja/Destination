package uz.kabirhoja.destination.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.kabirhoja.destination.data.data.Vocabulary
import uz.kabirhoja.destination.data.repository.MainRepository

class SearchViewModel(application: Application,private val repository: MainRepository) : AndroidViewModel(application) {
    private val _filteredWords = MutableLiveData<List<Vocabulary>>()
    val filteredWords: LiveData<List<Vocabulary>> get() = _filteredWords

    fun searchItems(query: String) {
        viewModelScope.launch {
            val result = repository.getSearchItems(query)
            _filteredWords.postValue(result)
        }
    }

}