package com.example.destination.data.repository

import android.content.Context
import com.example.destination.data.local.VocabularyDao
import com.example.destination.data.local.VocabularyEntity
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.ArrayList

class VocabularyRepository(private val dao: VocabularyDao, private val context: Context) {

    suspend fun insertAll(words: List<VocabularyEntity>) {
        withContext(Dispatchers.IO) {
            dao.insertAll(words)
        }
    }

    suspend fun getWordsByUnit(unit: String): List<VocabularyEntity> {
        return withContext(Dispatchers.IO) {
            dao.getWordsByUnit(unit)
        }
    }

    suspend fun getFilteredWords(units: ArrayList<Int>?, types: List<String>): List<VocabularyEntity> {
        return withContext(Dispatchers.IO) {
            dao.getFilteredWords(units, types)
        }
    }

    suspend fun getSearchItems(query: String): List<VocabularyEntity> {
        return withContext(Dispatchers.IO) {
            dao.searchItems(query)
        }
    }

    suspend fun getRowCount(): Int {
        return dao.getRowCount()
    }

    suspend fun loadJSONAndSaveToDatabase(fileName: String) {
        val jsonString = try {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return
        }

        try {
            val listType = object : TypeToken<List<VocabularyEntity>>() {}.type
            val wordList: List<VocabularyEntity> = Gson().fromJson(jsonString, listType)
            insertAll(wordList) // Save to Room
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            // Handle JSON parsing error (e.g., log or show a message)
        }
    }
}
