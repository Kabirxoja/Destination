package uz.kabirhoja.destination.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import uz.kabirhoja.destination.data.data.Vocabulary
import uz.kabirhoja.destination.data.repository.MainRepository
import kotlinx.coroutines.launch

class TestChoiceViewModel(application: Application, private val repository: MainRepository) : AndroidViewModel(application) {

    private val getFilteredList = MutableLiveData<List<Vocabulary>>()
    val getOptions:LiveData<List<Vocabulary>> get() = getFilteredList


    fun setOptions(units: List<Int>, types: List<String>){
        viewModelScope.launch {
            val options = repository.getFilteredWords(units, types)
            getFilteredList.value = options
        }
    }


}