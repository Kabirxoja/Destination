package uz.kabirhoja.destination.data.data

data class Vocabulary(
    val id: String,
    val type: String,
    val unit: String,
    val englishWord: String,
    val translatedWord: String,
    val definition: String,
    val exampleInEnglish: String,
    val exampleTranslatedWord: String,
    var isNoted: Int? = 0
)