package app.capstone.bansosadmin.data.source.remote.response

data class TrackResponse(
    val data: List<TrackItems>,
    val success: Boolean
)
data class TrackItems(
    val alamat: String,
    val created_at: String,
    val id: Int,
    val nik_penerima: Long,
    val status: String,
    val updated_at: String
)