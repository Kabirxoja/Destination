package uz.kabirhoja.destination.data.repository

import android.content.Context
import android.util.Log
import uz.kabirhoja.destination.data.local.VocabularyDao
import uz.kabirhoja.destination.ui.additions.MainSharedPreference
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import uz.kabirhoja.destination.data.data.UpdatedNotes
import uz.kabirhoja.destination.data.data.Vocabulary
import uz.kabirhoja.destination.data.local.VocabularyEntity
import java.io.IOException


private const val UPDATE_JSON_VERSION = 1

class MainRepository(
    private val dao: VocabularyDao,
    private val context: Context
) {
    private val sharedPreferences: MainSharedPreference = MainSharedPreference


    fun getWordsByUnit(unit: String): Flow<List<Vocabulary>> {
        val language = sharedPreferences.getLanguage(context)
        return if (language == "uz") {
            dao.getWordsInUzbek(unit)
        } else {
            dao.getWordsInKarakalpak(unit)
        }
    }


    suspend fun getFilteredWords(
        units: List<Int>,
        types: List<String>
    ): List<Vocabulary> {
        return withContext(Dispatchers.IO) {
            val language = sharedPreferences.getLanguage(context)
            if (language == "uz") {
                dao.getFilteredUzbekWords(units, types)
            } else {
                dao.getFilteredKarakalpakWords(units, types)
            }
        }
    }

    suspend fun getSearchItems(query: String): List<Vocabulary> {
        return withContext(Dispatchers.IO) {
            val language = sharedPreferences.getLanguage(context)
            if (language == "uz") {
                dao.searchInUzbek(query)
            } else {
                dao.searchInKarakaplak(query)
            }
        }
    }

    suspend fun updateItem(updatedNotes: UpdatedNotes) {
        withContext(Dispatchers.IO) {
            dao.updateItem(updatedNotes.id, updatedNotes.isNoted)
        }
    }

    fun getNotedWords(): Flow<List<Vocabulary>> {
        val language = sharedPreferences.getLanguage(context)
        Log.d("savedLang", language)
        return if (language == "uz") {
            dao.getNotedWordUzbek()
        } else {
            dao.getNotedWordKarakalpak()
        }
    }

    suspend fun loadJSONAndSaveToDatabase() {
        val sharedVersion = sharedPreferences.getUpdateJsonVersion(context)
        if (sharedVersion != UPDATE_JSON_VERSION) {
            sharedPreferences.saveUpdateJsonVersion(context, UPDATE_JSON_VERSION)
            val jsonString = try {
                context.assets.open("main_data.json").bufferedReader().use { it.readText() }
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
        }

    }
}
