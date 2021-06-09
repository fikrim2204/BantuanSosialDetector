package app.capstone.bantuansosialdetector.core.data.source.remote.response

data class PredictResponse(
    val signature_name: String? = null,
    val instances: List<Instance?>
)

data class Instance(
    val gaji: List<Double?>,
    val pekerjaan: List<Int?>,
    val tanggungan: List<Int?>,
    val umur: List<Int?>
)