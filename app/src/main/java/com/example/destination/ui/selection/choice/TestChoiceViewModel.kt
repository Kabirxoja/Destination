package com.example.destination.ui.selection.choice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TestChoiceViewModel : ViewModel() {

    private val _numberList = MutableLiveData<List<TestChoiceItem>>()
    val numberList: LiveData<List<TestChoiceItem>> = _numberList

    private val _bottomSheetData = MutableLiveData<Pair<Map<Int, Boolean>, Map<Int, Boolean>>>()
    val bottomSheetData: LiveData<Pair<Map<Int, Boolean>, Map<Int, Boolean>>> = _bottomSheetData



    fun setBottomSheetData(
        rowSelections: Map<Int, Boolean>,
        buttonSelections: Map<Int, Boolean>
    ) {
        _bottomSheetData.value = Pair(rowSelections, buttonSelections)
    }

    init {
        val list = listOf(
            TestChoiceItem(7, false),
            TestChoiceItem(12, false),
            TestChoiceItem(45, false),
            TestChoiceItem(67, false),
            TestChoiceItem(78, false)
        )
        _numberList.value = list

    }


}