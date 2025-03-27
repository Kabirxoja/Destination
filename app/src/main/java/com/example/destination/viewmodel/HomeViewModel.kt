package com.example.destination.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.destination.data.data.HomeItem

class HomeViewModel : ViewModel() {
    private val _homeTopics = MutableLiveData<List<HomeItem>>()
    val homeTopics: LiveData<List<HomeItem>> = _homeTopics

    init {
        val sampleData = listOf(
            HomeItem("Education Vocabulary",3),
            HomeItem("Crime Vocabulary",6),
            HomeItem("Diet, Health Vocabulary",9),
            HomeItem("Work Vocabulary",12),
            HomeItem("Environment Vocabulary",15),
            HomeItem("Science Vocabulary",18),
        )
        _homeTopics.value = sampleData
    }
}

