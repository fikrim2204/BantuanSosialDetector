package app.capstone.bansosadmin.data.source.remote.response

data class UpdateInsertResponse(
    val data: UpdateTrack,
    val message: String,
    val success: Boolean
)

data class UpdateTrack(
    val created_at: String,
    val id_track: Int,
    val lokasi: String,
    val updated_at: String,
    val waktu: String
)