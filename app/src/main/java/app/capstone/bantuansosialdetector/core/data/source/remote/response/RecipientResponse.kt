package app.capstone.bantuansosialdetector.core.data.source.remote.response

data class RecipientResponse(
    val data: List<RecipientRemote>?,
    val message: String? = null,
    val success: Boolean
)

data class RecipientRemote(
    val id: String? = null,
    val nik: Long? = null,
    val nama: String? = null,
    val no_hp: Long? = null,
    val alamat: String? = null,
    val gaji: Int? = null,
    val pekerjaan: Int? = null,
    val tanggungan: Int? = null,
    val umur: Int? = null,
    val status: Int? = null
)