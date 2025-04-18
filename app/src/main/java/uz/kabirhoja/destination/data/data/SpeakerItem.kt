package uz.kabirhoja.destination.data.data


data class SpeakerItem(
    val number: Int,
    val name: String,
    val locale: String,
    val quality: Int,
    val latency: Int,
    val requiresNetwork: Boolean
)
