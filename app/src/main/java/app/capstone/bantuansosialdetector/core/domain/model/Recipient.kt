package app.capstone.bantuansosialdetector.core.domain.model

data class Recipient(
    var id: String? = null,
    var noNik: Long? = null,
    var nama: String? = null,
    var noHp: Long? = null,
    var alamat: String? = null,
    var gaji: Double? = null,
    var pekerjaan: Int? = null,
    var tanggungan: Int? = null,
    var umur: Int? = null,
    var status: Int? = null
)
