package app.capstone.bantuansosialdetector.core.data.source.remote.response

data class RecipientResponse(
    val data: RecipientRemote,
    val message: String? = null,
    val success: Boolean
)

data class RecipientRemote(
    val id: String? = null,
    val nama: String? = null,
    val alamat: String? = null,
    val gaji: Int? = null,
    val pekerjaan: String? = null,
    val tanggungan: Int? = null,
    val umur: Int? = null,
    val status: Boolean? = null
)