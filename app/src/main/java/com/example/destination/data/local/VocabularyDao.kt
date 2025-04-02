package com.example.destination.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.ArrayList

@Dao
interface VocabularyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(words: List<VocabularyEntity>)

    @Query("SELECT * FROM vocabulary WHERE unit = :unit")
    fun getWordsByUnit(unit: String): Flow<List<VocabularyEntity>>

    @Query("SELECT COUNT(*) FROM vocabulary")
    suspend fun getRowCount(): Int

    @Query("SELECT * FROM vocabulary WHERE unit IN (:unit) AND type IN (:type)")
    suspend fun getFilteredWords(unit: ArrayList<Int>?, type: List<String>): List<VocabularyEntity>

    @Query("SELECT * FROM vocabulary WHERE englishWord LIKE '%' || :query || '%'")
    suspend fun searchItems(query: String): List<VocabularyEntity>

    @Query("select * from vocabulary where isNoted = 1")
    fun getNotedWords(): Flow<List<VocabularyEntity>>

    @Update
    suspend fun updateItem(vocabularyEntity: VocabularyEntity)

}