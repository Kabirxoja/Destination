package com.example.destination.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.ArrayList

@Dao
interface VocabularyDao {
    @Insert
    suspend fun insertAll(words: List<VocabularyEntity>)

    @Query("SELECT * FROM vocabulary WHERE unit = :unit")
    suspend fun getWordsByUnit(unit: String): List<VocabularyEntity>

    @Query("SELECT COUNT(*) FROM vocabulary")
    suspend fun getRowCount(): Int

    @Query("SELECT * FROM vocabulary WHERE unit IN (:unit) AND type IN (:type)")
    suspend fun getFilteredWords(unit: ArrayList<Int>?, type: List<String>): List<VocabularyEntity>

    @Query("SELECT * FROM vocabulary WHERE englishWord LIKE '%' || :query || '%'")
    suspend fun searchItems(query: String): List<VocabularyEntity>


}