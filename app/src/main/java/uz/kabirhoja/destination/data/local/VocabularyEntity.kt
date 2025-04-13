package uz.kabirhoja.destination.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "vocabulary")
data class VocabularyEntity(
    @PrimaryKey(autoGenerate = false)  val id: String,
    @SerializedName("theme") val type: String? = null,
    @SerializedName("unit") val unit: String? = null,
    @SerializedName("english_word") val englishWord: String? = null,
    @SerializedName("uzbek_word") val uzbekWord: String? = null,
    @SerializedName("karakalpak_word") val karakalpakWord: String? = null,
    @SerializedName("definition") val definition: String? = null,
    @SerializedName("example_in_english") val exampleInEnglish: String? = null,
    @SerializedName("example_in_uzbek") val exampleInUzbek: String? = null,
    @SerializedName("example_in_karakalpak") val exampleInKarakalpak: String? = null,
    val isNoted: Int? = 0
)