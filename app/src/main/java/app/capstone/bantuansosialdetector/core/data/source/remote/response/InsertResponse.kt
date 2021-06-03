package app.capstone.bantuansosialdetector.core.data.source.remote.response

data class InsertResponse(
    val data: InsertItems,
    val message: String,
    val success: Boolean
)

data class InsertItems(
    val alamat: String,
    val created_at: String,
    val gaji: String,
    val id: String,
    val nama: String,
    val nik: String,
    val no_hp: String,
    val pekerjaan: String,
    val status: Int?,
    val tanggungan: String,
    val umur: String,
    val updated_at: String
)