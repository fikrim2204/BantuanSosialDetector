package app.capstone.bansosadmin.data.source.remote.response

data class RecepientResponse(
    val data: List<Recepient>,
    val success: Boolean
)

data class Recepient(
    val alamat: String,
    val created_at: String,
    val gaji: Int,
    val id: String,
    val nama: String,
    val nik: Long,
    val no_hp: Long,
    val pekerjaan: String,
    val status: Int,
    val tanggungan: Int,
    val umur: Int,
    val updated_at: String
)