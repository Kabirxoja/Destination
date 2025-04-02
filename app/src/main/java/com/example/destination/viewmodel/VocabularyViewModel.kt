package com.example.destination.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.destination.data.local.AppDatabase
import com.example.destination.data.local.VocabularyEntity
import com.example.destination.data.repository.MainRepository
import com.example.destination.data.data.VocabularyItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class VocabularyViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MainRepository

    private val _wordsByUnit = MutableStateFlow<List<VocabularyItem>>(emptyList())
    val wordsByUnit: StateFlow<List<VocabularyItem>> get() = _wordsByUnit

    init {
        val database = AppDatabase.getDatabase(application)
        repository = MainRepository(database.vocabularyDao(), application)
    }

    fun observeWordsByUnit(unit: String) {
        viewModelScope.launch {
            repository.getWordsByUnit(unit)
                .map { words -> words.map { it.toParentItem() } }
                .collect { wordsList ->
                    _wordsByUnit.value = wordsList
                }
        }
    }

    fun updateItem(vocabularyItem: VocabularyItem) {
        viewModelScope.launch {
            repository.updateItem(
                VocabularyEntity(
                    id = vocabularyItem.id,
                    unit = vocabularyItem.unit,
                    type = vocabularyItem.type,
                    englishWord = vocabularyItem.enWord,
                    uzbekWord = vocabularyItem.uzWord,
                    definition = vocabularyItem.definition,
                    exampleInEnglish = vocabularyItem.enExample,
                    exampleInUzbek = vocabularyItem.uzExample,
                    exampleInKarakalpak = vocabularyItem.kaExample,
                    karakalpakWord = vocabularyItem.kaWord,
                    isNoted = vocabularyItem.isNoted
                )
            )
        }
    }

    // Convert `VocabularyEntity` to `ParentItem`
    private fun VocabularyEntity.toParentItem() = VocabularyItem(
        unit = unit ?: "",
        type = type ?: "",
        enWord = englishWord ?: "",
        uzWord = uzbekWord ?: "",
        kaWord = karakalpakWord ?: "",
        definition = definition ?: "",
        enExample = exampleInEnglish ?: "",
        uzExample = exampleInUzbek ?: "",
        kaExample = exampleInKarakalpak ?: "",
        isNoted = isNoted ?: 0,
        id = id
    )
}
