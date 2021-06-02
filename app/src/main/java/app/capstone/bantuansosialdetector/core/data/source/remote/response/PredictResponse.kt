package app.capstone.bantuansosialdetector.core.data.source.remote.response

data class PredictResponse(
    val signature_name: String? = null,
    val instances: List<Instance>? = null
)

data class Instance(
    val gaji: List<Double>? = null,
    val pekerjaan: List<Int>? = null,
    val tanggungan: List<Int>? = null,
    val umur: List<Int>? = null
)