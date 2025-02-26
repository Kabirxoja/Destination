package com.example.destination.ui.test

import android.graphics.pdf.models.ListItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.destination.ui.home.vocabulary.ChildItem
import com.example.destination.ui.home.vocabulary.ParentItem

class TestViewModel : ViewModel() {

    private val _numberList = MutableLiveData<List<Int>>()
    val numberList:LiveData<List<Int>> = _numberList

    init {
        val list = listOf(1, 4, 6, 8, 13, 15, 17, 19, 21)
        _numberList.value = list
    }



}