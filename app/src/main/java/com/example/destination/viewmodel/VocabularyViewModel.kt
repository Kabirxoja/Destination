package com.example.destination.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.destination.data.local.AppDatabase
import com.example.destination.data.local.VocabularyEntity
import com.example.destination.data.repository.VocabularyRepository
import com.example.destination.data.data.VocabularyItem
import kotlinx.coroutines.launch
import java.util.Locale

class VocabularyViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: VocabularyRepository

    private val _wordsByUnit = MutableLiveData<List<VocabularyItem>>()
    val wordsByUnit: LiveData<List<VocabularyItem>> get() = _wordsByUnit

    init {
        val database = AppDatabase.getDatabase(application)
        repository = VocabularyRepository(database.vocabularyDao(), application)
    }

    fun getWordsByUnit(unit: String) {
        viewModelScope.launch {
            val words = repository.getWordsByUnit(unit)

            // 🔍 Debugging: Check raw words before sorting
            println("=== BEFORE SORTING ===")
            words.forEach { println("Unit=${it.unit}, Type=${it.type}, Word=${it.englishWord}") }

            // Apply sorting: First by unit, then by type priority
            val sortedWords = words.sortedWith(
                compareBy<VocabularyEntity> { it.unit?.toIntOrNull() ?: Int.MAX_VALUE }.thenBy { typePriority(it.type) }
            )

            // 🔍 Debugging: Check sorted words after sorting
            println("=== AFTER SORTING ===")
            sortedWords.forEach { println("Unit=${it.unit}, Type=${it.type}, Word=${it.englishWord}") }

            // Remove duplicates (if necessary)
            val uniqueWords = sortedWords.distinctBy { it.englishWord to it.type }

            // 🔍 Debugging: Check unique words after removing duplicates
            println("=== AFTER REMOVING DUPLICATES ===")
            uniqueWords.forEach { println("Unit=${it.unit}, Type=${it.type}, Word=${it.englishWord}") }

            // Convert to ParentItem list
            _wordsByUnit.postValue(uniqueWords.map { it.toParentItem() })
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

        return priorityMap.indexOf(type?.lowercase(Locale.ROOT)?.trim()).takeIf { it != -1 } ?: priorityMap.size
    }

    // Convert `VocabularyEntity` to `ParentItem`
    private fun VocabularyEntity.toParentItem() = VocabularyItem(
        unit = unit.toString(),
        type = type.toString(),
        enWord = englishWord.toString(),
        uzWord = uzbekWord.toString(),
        definition = definition.toString(),
        enExample = exampleInEnglish.toString(),
        uzExample = exampleInUzbek.toString()
    )
}
