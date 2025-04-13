package uz.kabirhoja.destination.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import uz.kabirhoja.destination.data.data.UpdatedNotes
import uz.kabirhoja.destination.data.data.Vocabulary
import uz.kabirhoja.destination.data.local.AppDatabase
import uz.kabirhoja.destination.data.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class VocabularyViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MainRepository

    private val _wordsByUnit = MutableStateFlow<List<Vocabulary>>(emptyList())
    val wordsByUnit: StateFlow<List<Vocabulary>> get() = _wordsByUnit

    init {
        val database = AppDatabase.getDatabase(application)
        repository = MainRepository(database.vocabularyDao(), application)
    }

    fun observeWordsByUnit(unit: String) {
        viewModelScope.launch {
            repository.getWordsByUnit(unit).map { it }.collect { wordsList ->
                _wordsByUnit.value = wordsList
            }
        }
    }

    fun updateItem(updatedNotes: UpdatedNotes) {
        viewModelScope.launch {
            repository.updateItem(updatedNotes)
        }
    }
}
