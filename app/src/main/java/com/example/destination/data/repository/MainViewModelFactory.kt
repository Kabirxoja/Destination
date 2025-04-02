package com.example.destination.data.repository

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.destination.viewmodel.HomeViewModel
import com.example.destination.viewmodel.NotesViewModel
import com.example.destination.viewmodel.ResultViewModel
import com.example.destination.viewmodel.SearchViewModel
import com.example.destination.viewmodel.SettingsViewModel
import com.example.destination.viewmodel.TestChoiceViewModel
import com.example.destination.viewmodel.TestMainViewModel
import com.example.destination.viewmodel.VocabularyViewModel

class MainViewModelFactory(private var application: Application) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)){
            return NotesViewModel(application) as T
        }

        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(application) as T
        }

        if (modelClass.isAssignableFrom(SearchViewModel::class.java)){
            return SearchViewModel(application) as T
        }

        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)){
            return SettingsViewModel(application) as T
        }

        if (modelClass.isAssignableFrom(TestChoiceViewModel::class.java)){
            return TestChoiceViewModel(application) as T
        }

        if (modelClass.isAssignableFrom(TestMainViewModel::class.java)){
            return TestMainViewModel(application) as T
        }

        if (modelClass.isAssignableFrom(VocabularyViewModel::class.java)){
            return VocabularyViewModel(application) as T
        }

        if (modelClass.isAssignableFrom(ResultViewModel::class.java)){
            return ResultViewModel(application) as T
        }


        throw IllegalArgumentException("Unknown ViewModel class")
    }
}