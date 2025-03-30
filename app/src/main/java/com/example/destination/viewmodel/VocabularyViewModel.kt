package com.example.destination.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.destination.data.local.AppDatabase
import com.example.destination.data.local.VocabularyEntity
import com.example.destination.data.repository.VocabularyRepository
import com.example.destination.data.data.VocabularyItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Locale

class VocabularyViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: VocabularyRepository

    private val _wordsByUnit = MutableStateFlow<List<VocabularyItem>>(emptyList())
    val wordsByUnit: StateFlow<List<VocabularyItem>> get() = _wordsByUnit

    init {
        val database = AppDatabase.getDatabase(application)
        repository = VocabularyRepository(database.vocabularyDao(), application)
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


    // Define type priority for sorting
    private fun typePriority(type: String?): Int {
        val priorityMap = listOf(
            "topic_vocabulary",
            "phrasal_verbs",
            "prepositional_phrases",
            "word_formation",
            "word_patterns"
        )
        return priorityMap.indexOf(type?.lowercase(Locale.ROOT)?.trim()).takeIf { it != -1 }
            ?: priorityMap.size
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
                    isNoted = vocabularyItem.isNoted
                )
            )
        }
        // ? update again
//        getWordsByUnit(vocabularyItem.unit)
    }

    // Convert `VocabularyEntity` to `ParentItem`
    private fun VocabularyEntity.toParentItem() = VocabularyItem(
        unit = unit.toString(),
        type = type.toString(),
        enWord = englishWord.toString(),
        uzWord = uzbekWord.toString(),
        definition = definition.toString(),
        enExample = exampleInEnglish.toString(),
        uzExample = exampleInUzbek.toString(),
        isNoted = isNoted,
        id = id
    )
}
