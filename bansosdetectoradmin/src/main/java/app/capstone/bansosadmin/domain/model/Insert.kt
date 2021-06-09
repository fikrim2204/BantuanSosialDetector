package app.capstone.bansosadmin.domain.model

data class Insert(
    val nik_penerima: String,
    val id: Int? = null,
    val alamat: String? = null
)
