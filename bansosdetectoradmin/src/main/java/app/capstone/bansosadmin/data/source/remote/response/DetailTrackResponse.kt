package app.capstone.bansosadmin.data.source.remote.response

data class DetailTrackResponse(
    val data: List<DetailTrackItems>,
    val success: Boolean
)

data class DetailTrackItems(
    val created_at: String,
    val id_track: Int,
    val lokasi: String,
    val updated_at: String,
    val waktu: String
)