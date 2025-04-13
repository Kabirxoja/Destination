package uz.kabirhoja.destination.data.data

data class VocabularyItem(
    val unit: String,
    val type: String,
    val enWord: String,
    val uzWord: String,
    val kaWord: String,
    val definition: String,
    val enExample: String,
    val uzExample: String,
    val kaExample: String,
    var isNoted: Int? = 0,
    val id: String
)
