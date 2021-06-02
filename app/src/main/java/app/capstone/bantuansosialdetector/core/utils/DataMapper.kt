package app.capstone.bantuansosialdetector.core.utils

import app.capstone.bantuansosialdetector.core.data.source.remote.response.RecipientRemote
import app.capstone.bantuansosialdetector.core.data.source.remote.response.RecipientResponse
import app.capstone.bantuansosialdetector.core.domain.model.Recipient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {
    fun mapResponsesToDomain(input: RecipientRemote) =
        app.capstone.bantuansosialdetector.core.domain.model.Recipient(
            id = input.id,
            nama = input.nama,
            alamat = input.alamat,
            gaji = input.gaji,
            pekerjaan = input.pekerjaan,
            tanggungan = input.tanggungan,
            umur = input.umur,
            status = input.status
        )

    fun mapDomainToResponse(input: Recipient) =
        RecipientRemote(
            id = input.id,
            nama = input.nama,
            alamat = input.alamat,
            gaji = input.gaji,
            pekerjaan = input.pekerjaan,
            tanggungan = input.tanggungan,
            umur = input.umur,
            status = input.status
        )

    fun mapResponsesToDomainWithFlow(input: RecipientResponse): Flow<Recipient> {
        return flowOf(
            Recipient(
                id = input.data.id,
                nama = input.data.nama,
                alamat = input.data.alamat,
                gaji = input.data.gaji,
                pekerjaan = input.data.pekerjaan,
                tanggungan = input.data.tanggungan,
                umur = input.data.umur,
                status = input.data.status
            )
        )
    }
}