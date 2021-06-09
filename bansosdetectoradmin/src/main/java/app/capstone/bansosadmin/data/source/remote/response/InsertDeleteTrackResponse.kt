package app.capstone.bansosadmin.data.source.remote.response

data class InsertDeleteTrackResponse(
    val data: InsertDeleteTrack,
    val message: String,
    val success: Boolean
)

data class InsertDeleteTrack(
    val alamat: String,
    val created_at: String,
    val id: Int,
    val nik_penerima: Long,
    val status: String,
    val updated_at: String
)