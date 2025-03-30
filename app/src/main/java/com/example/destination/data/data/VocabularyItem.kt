package com.example.destination.data.data

data class VocabularyItem(
    val unit: String,
    val type: String,
    val enWord: String,
    val uzWord: String,
    val definition: String,
    val enExample: String,
    val uzExample: String,
    var isNoted: Int? = 0,
    val id: Int? = null
)