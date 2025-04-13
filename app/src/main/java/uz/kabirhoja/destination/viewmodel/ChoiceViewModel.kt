package uz.kabirhoja.destination.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.kabirhoja.destination.data.data.TestChoiceItem
import uz.kabirhoja.destination.data.data.OptionItem

class ChoiceViewModel(application: Application) : AndroidViewModel(application) {

    private val _numberList = MutableLiveData<List<TestChoiceItem>>()
    val numberList: LiveData<List<TestChoiceItem>> = _numberList

    private val _optionList = MutableLiveData<List<OptionItem>>()
    val optionList: LiveData<List<OptionItem>> = _optionList

    fun setOptionList(list: List<OptionItem>) {
        _optionList.value = list
    }

    fun setNumberList(list: List<TestChoiceItem>) {
        _numberList.value = list
    }


}
