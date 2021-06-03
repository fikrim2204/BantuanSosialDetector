package app.capstone.bantuansosialdetector.core.domain.model

data class Predict(
    val gaji: List<Double?>,
    val pekerjaan: List<Int?>,
    val tanggungan: List<Int?>,
    val umur: List<Int?>
)
