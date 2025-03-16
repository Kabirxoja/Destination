package com.example.destination.ui.home.saving

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VocabularyDao {
    @Insert
    suspend fun insertAll(words: List<VocabularyEntity>)

    @Query("SELECT * FROM vocabulary WHERE unit = :unit")
    suspend fun getWordsByUnit(unit: String): List<VocabularyEntity>
}