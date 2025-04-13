package uz.kabirhoja.destination.data.data

data class WordItem(
    val uzbekWord: String,
    val englishWord: String,
    val type: String,
    val unit: String,
    val definition: String,
    var status: Int  // 0 = unanswered, 1 = correct, -1 = incorrect
)
