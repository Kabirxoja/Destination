package com.example.destination.ui.home.saving

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.destination.ui.home.vocabulary.VocabularyViewModel

class VocabViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VocabViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VocabViewModel(application) as T
        }
        if (modelClass.isAssignableFrom(VocabularyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VocabularyViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}