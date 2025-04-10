package com.example.destination.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.destination.data.data.UpdatedNotes
import com.example.destination.data.data.Vocabulary
import kotlinx.coroutines.flow.Flow
import java.util.ArrayList

@Dao
interface VocabularyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(words: List<VocabularyEntity>)

    @Query("SELECT id, type AS type, unit, englishWord AS englishWord, uzbekWord AS translatedWord, definition,exampleInEnglish AS exampleInEnglish, exampleInUzbek AS exampleTranslatedWord, isNoted FROM vocabulary WHERE unit = :unit")
    fun getWordsInUzbek(unit: String): Flow<List<Vocabulary>>

    @Query("SELECT id, type AS type, unit, englishWord AS englishWord, karakalpakWord AS translatedWord, definition,exampleInEnglish AS exampleInEnglish, exampleInKarakalpak AS exampleTranslatedWord, isNoted FROM vocabulary WHERE unit = :unit")
    fun getWordsInKarakalpak(unit: String): Flow<List<Vocabulary>>


    @Query("SELECT * FROM vocabulary WHERE unit IN (:unit) AND type IN (:type)")
    suspend fun getFilteredWords(unit: List<Int>, type: List<String>): List<VocabularyEntity>

    @Query("SELECT * FROM vocabulary WHERE englishWord LIKE '%' || :query || '%'")
    suspend fun searchItems(query: String): List<VocabularyEntity>



    @Query("SELECT id, type AS type, unit, englishWord AS englishWord, karakalpakWord AS translatedWord, definition,exampleInEnglish AS exampleInEnglish, exampleInKarakalpak AS exampleTranslatedWord, isNoted from vocabulary where isNoted = 1")
    fun getNotedWordKarakalpak(): Flow<List<Vocabulary>>

    @Query("SELECT id, type AS type, unit, englishWord AS englishWord, uzbekWord AS translatedWord, definition,exampleInEnglish AS exampleInEnglish, exampleInUzbek AS exampleTranslatedWord, isNoted from vocabulary where isNoted = 1")
    fun getNotedWordUzbek(): Flow<List<Vocabulary>>



    @Query("update vocabulary set isNoted = :isNoted where id = :id")
    suspend fun updateItem(id: String, isNoted: Int)


}