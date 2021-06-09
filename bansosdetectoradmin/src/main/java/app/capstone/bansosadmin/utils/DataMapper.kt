package app.capstone.bansosadmin.utils

import app.capstone.bansosadmin.data.source.remote.response.DetailTrackItems
import app.capstone.bansosadmin.data.source.remote.response.InsertDeleteTrack
import app.capstone.bansosadmin.data.source.remote.response.Recepient
import app.capstone.bansosadmin.data.source.remote.response.TrackItems
import app.capstone.bansosadmin.domain.model.DetailTracks
import app.capstone.bansosadmin.domain.model.Insert
import app.capstone.bansosadmin.domain.model.Recipients
import app.capstone.bansosadmin.domain.model.Tracks
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {
    fun mapResponsesToDomain(input: List<Recepient>): Flow<List<Recipients>> {
        val recipientsList = ArrayList<Recipients>()
        input.map {
            val recipients = Recipients(
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
            recipientsList.add(recipients)
        }
        return flowOf(recipientsList)
    }

    fun mapResponsesInsertToDomain(input: List<InsertDeleteTrack>): Flow<List<Insert>> {
        val insertList = ArrayList<Insert>()
        input.map {
            val insert = Insert(
                it.nik_penerima.toString(),
                it.id,
                it.alamat
            )
            insertList.add(insert)
        }
        return flowOf(insertList)
    }

    fun mapResponsesTrackToDomain(input: List<TrackItems>): Flow<List<Tracks>> {
        val trackList = ArrayList<Tracks>()
        input.map {
            val tracks = Tracks(
                it.alamat,
                it.id,
                it.nik_penerima,
                it.status,
                it.updated_at
            )
            trackList.add(tracks)
        }
        return flowOf(trackList)
    }

    fun mapResponsesDetailToDomain(input: List<DetailTrackItems>): Flow<List<DetailTracks>> {
        val trackList = ArrayList<DetailTracks>()
        input.map {
            val tracks = DetailTracks(
                it.lokasi, it.waktu
            )
            trackList.add(tracks)
        }
        return flowOf(trackList)
    }

    fun mapResponseFinishToDomain(type: InsertDeleteTrack): Flow<Insert> {
        return flowOf(
            Insert(
                type.nik_penerima.toString(),
                type.id,
                type.alamat
            )
        )
    }
}