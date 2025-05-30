package uz.kabirhoja.destination.data.repository

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.kabirhoja.destination.data.local.AppDatabase
import uz.kabirhoja.destination.viewmodel.HomeViewModel
import uz.kabirhoja.destination.viewmodel.ResultViewModel
import uz.kabirhoja.destination.viewmodel.SearchViewModel
import uz.kabirhoja.destination.viewmodel.ChoiceViewModel
import uz.kabirhoja.destination.viewmodel.NotesViewModel
import uz.kabirhoja.destination.viewmodel.SettingsViewModel
import uz.kabirhoja.destination.viewmodel.TestChoiceViewModel
import uz.kabirhoja.destination.viewmodel.TestWriteViewModel
import uz.kabirhoja.destination.viewmodel.VocabularyViewModel

class MainViewModelFactory(private var application: Application) : ViewModelProvider.Factory {

    val dao = AppDatabase.getDatabase(application).vocabularyDao()
    val repository = MainRepository(dao, application)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            return NotesViewModel(application, repository) as T
        }

        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(application, repository) as T
        }

        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(application, repository) as T
        }

        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(application) as T
        }

        if (modelClass.isAssignableFrom(ChoiceViewModel::class.java)) {
            return ChoiceViewModel(application) as T
        }

        if (modelClass.isAssignableFrom(TestWriteViewModel::class.java)) {
            return TestWriteViewModel(application, repository) as T
        }

        if (modelClass.isAssignableFrom(VocabularyViewModel::class.java)) {
            return VocabularyViewModel(application, repository) as T
        }

        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            return ResultViewModel(application) as T
        }
        if (modelClass.isAssignableFrom(TestChoiceViewModel::class.java)) {
            return TestChoiceViewModel(application, repository) as T
        }


        throw IllegalArgumentException("Unknown ViewModel class")
    }
}