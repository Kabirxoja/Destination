package uz.kabirhoja.destination.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.kabirhoja.destination.data.data.Vocabulary

@Dao
interface VocabularyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(words: List<VocabularyEntity>)

    @Query("SELECT id, type AS type, unit, englishWord AS englishWord, uzbekWord AS translatedWord, definition,exampleInEnglish AS exampleInEnglish, exampleInUzbek AS exampleTranslatedWord, isNoted FROM vocabulary WHERE unit = :unit")
    fun getWordsInUzbek(unit: String): Flow<List<Vocabulary>>
    @Query("SELECT id, type AS type, unit, englishWord AS englishWord, karakalpakWord AS translatedWord, definition,exampleInEnglish AS exampleInEnglish, exampleInKarakalpak AS exampleTranslatedWord, isNoted FROM vocabulary WHERE unit = :unit")
    fun getWordsInKarakalpak(unit: String): Flow<List<Vocabulary>>


    @Query("SELECT id, type AS type, unit, englishWord AS englishWord, karakalpakWord AS translatedWord, definition,exampleInEnglish AS exampleInEnglish, exampleInKarakalpak AS exampleTranslatedWord, isNoted from vocabulary WHERE unit IN (:unit) AND type IN (:type)")
    suspend fun getFilteredKarakalpakWords(unit: List<Int>, type: List<String>): List<Vocabulary>
    @Query("SELECT id, type AS type, unit, englishWord AS englishWord, uzbekWord AS translatedWord, definition,exampleInEnglish AS exampleInEnglish, exampleInUzbek AS exampleTranslatedWord, isNoted from vocabulary WHERE unit IN (:unit) AND type IN (:type)")
    suspend fun getFilteredUzbekWords(unit: List<Int>, type: List<String>): List<Vocabulary>


    @Query("SELECT id, type AS type, unit, englishWord AS englishWord, karakalpakWord AS translatedWord, definition,exampleInEnglish AS exampleInEnglish, exampleInKarakalpak AS exampleTranslatedWord, isNoted from vocabulary WHERE englishWord LIKE '%' || :query || '%'")
    suspend fun searchInKarakaplak(query: String): List<Vocabulary>
    @Query("SELECT id, type AS type, unit, englishWord AS englishWord, uzbekWord AS translatedWord, definition,exampleInEnglish AS exampleInEnglish, exampleInUzbek AS exampleTranslatedWord, isNoted from vocabulary WHERE englishWord LIKE '%' || :query || '%'")
    suspend fun searchInUzbek(query: String): List<Vocabulary>



    @Query("SELECT id, type AS type, unit, englishWord AS englishWord, karakalpakWord AS translatedWord, definition,exampleInEnglish AS exampleInEnglish, exampleInKarakalpak AS exampleTranslatedWord, isNoted from vocabulary WHERE isNoted = 1")
    fun getNotedWordKarakalpak(): Flow<List<Vocabulary>>
    @Query("SELECT id, type AS type, unit, englishWord AS englishWord, uzbekWord AS translatedWord, definition,exampleInEnglish AS exampleInEnglish, exampleInUzbek AS exampleTranslatedWord, isNoted from vocabulary WHERE isNoted = 1")
    fun getNotedWordUzbek(): Flow<List<Vocabulary>>



    @Query("update vocabulary set isNoted = :isNoted WHERE id = :id")
    suspend fun updateItem(id: String, isNoted: Int)


}