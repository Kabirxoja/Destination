package com.example.destination.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


import com.google.gson.annotations.SerializedName

@Entity(tableName = "vocabulary")
data class VocabularyEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val type: String? = null,
    @SerializedName("unit") val unit: String? = null,
    @SerializedName("english_word") val englishWord: String? = null,
    @SerializedName("uzbek_word") val uzbekWord: String? = null,
    val definition: String? = null,
    @SerializedName("example_in_english") val exampleInEnglish: String? = null,
    @SerializedName("example_in_uzbek") val exampleInUzbek: String? = null,
    val isNoted: Int? = 0
)