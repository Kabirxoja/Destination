package com.example.destination.ui.test

import android.graphics.pdf.models.ListItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.destination.ui.home.vocabulary.ChildItem
import com.example.destination.ui.home.vocabulary.ParentItem

class TestViewModel : ViewModel() {

    private val _numberList = MutableLiveData<List<TestItem>>()
    val numberList: LiveData<List<TestItem>> = _numberList

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
            TestItem(7, false),
            TestItem(12, false),
            TestItem(45, false),
            TestItem(67, false),
            TestItem(78, false)
        )
        _numberList.value = list
    }


}