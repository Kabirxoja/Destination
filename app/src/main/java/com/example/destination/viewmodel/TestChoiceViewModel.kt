package com.example.destination.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.destination.data.data.TestChoiceItem

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
            TestChoiceItem("Fun and games", 3, false),
            TestChoiceItem("Learning and doing", 6, false),
            TestChoiceItem("Coming and going", 9, false),
            TestChoiceItem("Friends and relations", 12, false),
            TestChoiceItem("Buying and selling", 15, false),
            TestChoiceItem("Inventions and discoveries", 18, false),
            TestChoiceItem("Sending and receiving", 21, false),
            TestChoiceItem("People and daily life", 24, false),
            TestChoiceItem("Working and earning", 27, false),
            TestChoiceItem("Body and lifestyle", 30, false),
            TestChoiceItem("Creating and building", 33, false),
            TestChoiceItem("Nature and the universe", 36, false),
            TestChoiceItem("Laughing and crying", 39, false),
            TestChoiceItem("Problems and solutions", 42, false)
        )




        _numberList.value = list

    }


}
