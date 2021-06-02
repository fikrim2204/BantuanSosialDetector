package app.capstone.bantuansosialdetector.core.domain.model

data class Recipient(
    val id: String? = null,
    val noNik: Long? = null,
    val nama: String? = null,
    val noHp: Long? = null,
    val alamat: String? = null,
    var gaji: Double? = null,
    var pekerjaan: Int? = null,
    var tanggungan: Int? = null,
    var umur: Int? = null,
    val status: Int? = null
)
