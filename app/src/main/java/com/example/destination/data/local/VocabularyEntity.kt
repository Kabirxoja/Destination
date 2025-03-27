package com.example.destination.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


import com.google.gson.annotations.SerializedName

@Entity(tableName = "vocabulary")
data class VocabularyEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val type: String? = null, // Make nullable
    @SerializedName("unit") val unit: String? = null, // Make nullable
    @SerializedName("english_word") val englishWord: String? = null, // Make nullable
    @SerializedName("uzbek_word") val uzbekWord: String? = null, // Make nullable
    val definition: String? = null, // Make nullable
    @SerializedName("example_in_english") val exampleInEnglish: String? = null, // Make nullable
    @SerializedName("example_in_uzbek") val exampleInUzbek: String? = null // Make nullable
)