package uz.kabirhoja.destination.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.kabirhoja.destination.data.data.Vocabulary
import uz.kabirhoja.destination.data.local.AppDatabase
import uz.kabirhoja.destination.data.repository.MainRepository

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