package app.capstone.bantuansosialdetector.core.data.source.remote.response

data class TrackingResponse(
    val data: List<TrackingRemote>? = null,
    val success: Boolean
)

data class TrackingRemote(
    val id: Int? = null,
    val nik_penerima: Long? = null,
    val alamat: String? = null,
    val status: String? = null,
)