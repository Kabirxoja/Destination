package uz.kabirhoja.destination.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import uz.kabirhoja.destination.data.local.AppDatabase
import uz.kabirhoja.destination.data.repository.MainRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MainRepository

    init {
        val database = AppDatabase.getDatabase(application)
        repository = MainRepository(database.vocabularyDao(), application)
    }


    //ask for Sherali this place was written correctly
    fun setLoadJson() {
        viewModelScope.launch {
            repository.loadJSONAndSaveToDatabase()
        }
    }



}