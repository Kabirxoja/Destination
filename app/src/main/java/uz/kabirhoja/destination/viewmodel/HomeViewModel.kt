package uz.kabirhoja.destination.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import uz.kabirhoja.destination.data.repository.MainRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application, private val repository: MainRepository) : AndroidViewModel(application) {

    fun setLoadJson() {
        viewModelScope.launch {
            repository.loadJSONAndSaveToDatabase()
        }
    }

}