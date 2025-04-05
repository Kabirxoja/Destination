package com.example.destination.data.repository

import android.content.Context
import android.util.Log
import com.example.destination.data.local.VocabularyDao
import com.example.destination.data.local.VocabularyEntity
import com.example.destination.ui.additions.MainSharedPreference
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.ArrayList


private const val UPDATE_JSON_VERSION = 1

class MainRepository(
    private val dao: VocabularyDao,
    private val context: Context
) {
    private val sharedPreferences: MainSharedPreference = MainSharedPreference

    fun getWordsByUnit(unit: String): Flow<List<VocabularyEntity>> {
        return dao.getWordsByUnit(unit)
    }

    suspend fun getFilteredWords(
        units: ArrayList<Int>?,
        types: List<String>
    ): List<VocabularyEntity> {
        return withContext(Dispatchers.IO) {
            dao.getFilteredWords(units, types)
        }
    }

    suspend fun getSearchItems(query: String): List<VocabularyEntity> {
        return withContext(Dispatchers.IO) {
            dao.searchItems(query)
        }
    }

    suspend fun updateItem(vocabularyEntity: VocabularyEntity) {
        withContext(Dispatchers.IO) {
            dao.updateItem(vocabularyEntity)
        }
    }

    fun getNotedWords(): Flow<List<VocabularyEntity>> = dao.getNotedWords()


    suspend fun getRowCount(): Int {
        return dao.getRowCount()
    }

    suspend fun loadJSONAndSaveToDatabase() {
        val sharedVersion = sharedPreferences.getUpdateJsonVersion(context)
        if (sharedVersion!=UPDATE_JSON_VERSION){
            sharedPreferences.saveUpdateJsonVersion(context, UPDATE_JSON_VERSION)
            val jsonString = try {
                context.assets.open("converted.json").bufferedReader().use { it.readText() }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                return
            }

            try {
                val listType = object : TypeToken<List<VocabularyEntity>>() {}.type
                val wordList: List<VocabularyEntity> = Gson().fromJson(jsonString, listType)
                //room insert
                dao.insertAll(wordList)
            } catch (e: JsonSyntaxException) {
                e.printStackTrace()
            }
            Log.d("insertWordStatus","Updated")
        }else{
            Log.d("insertWordStatus","do not update")
        }

    }
}
