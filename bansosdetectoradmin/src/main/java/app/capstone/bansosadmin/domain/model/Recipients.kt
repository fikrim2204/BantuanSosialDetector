package app.capstone.bansosadmin.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipients(
    val alamat: String?,
    val created_at: String?,
    val gaji: Int?,
    val id: String?,
    val nama: String?,
    val nik: Long?,
    val no_hp: Long?,
    val pekerjaan: String?,
    val status: Int?,
    val tanggungan: Int?,
    val umur: Int?,
    val updated_at: String?
) : Parcelable