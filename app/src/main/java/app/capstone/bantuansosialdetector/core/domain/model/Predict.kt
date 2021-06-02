package app.capstone.bantuansosialdetector.core.domain.model

data class Predict(
    val gaji: List<Double>? = null,
    val pekerjaan: List<Int>? = null,
    val tanggungan: List<Int>? = null,
    val umur: List<Int>? = null
)
