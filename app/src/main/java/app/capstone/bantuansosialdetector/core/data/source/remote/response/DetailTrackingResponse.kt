package app.capstone.bantuansosialdetector.core.data.source.remote.response

data class DetailTrackingResponse(
    val data: List<DetailTrackingRemote>? = null,
    val success: Boolean
)

data class DetailTrackingRemote(
    val id_track: Int? = null,
    val lokasi: String? = null,
    val waktu: String? = null
)