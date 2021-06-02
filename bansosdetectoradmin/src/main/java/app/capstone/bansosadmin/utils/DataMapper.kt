package app.capstone.bansosadmin.utils

import app.capstone.bansosadmin.data.source.remote.response.Recepient
import app.capstone.bansosadmin.domain.model.Penerima
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {
    fun mapResponsesToDomain(input: List<Recepient>): Flow<List<Penerima>> {
        val penerimalist = ArrayList<Penerima>()
        input.map {
            val penerima = Penerima(
                it.alamat,
                it.created_at,
                it.gaji,
                it.id,
                it.nama,
                it.nik,
                it.no_hp,
                it.pekerjaan,
                it.status,
                it.tanggungan,
                it.umur,
                it.updated_at
            )
            penerimalist.add(penerima)
        }
        return flowOf(penerimalist)
    }

}