package app.capstone.bantuansosialdetector.core.utils

import app.capstone.bantuansosialdetector.core.data.source.remote.response.*
import app.capstone.bantuansosialdetector.core.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {

    fun mapResponsesToDomainWithFlow(input: List<RecipientRemote>?): Flow<List<Recipient>> {
        val list = ArrayList<Recipient>()
        input?.map {
            val recipient = Recipient(
                id = it.id,
                noNik = it.nik,
                nama = it.nama,
                noHp = it.no_hp,
                alamat = it.alamat,
                gaji = it.gaji,
                pekerjaan = it.pekerjaan,
                tanggungan = it.tanggungan,
                umur = it.umur,
                status = it.status
            )
            list.add(recipient)
        }
        return flowOf(list)
    }

    fun mapResponsesInsertToDomain(input: InsertResponse): Flow<Insert> {
        return flowOf(
            Insert(
                input.data.alamat,
                input.data.gaji,
                input.data.id,
                input.data.nama,
                input.data.nik,
                input.data.no_hp,
                input.data.pekerjaan,
                input.data.status,
                input.data.tanggungan,
                input.data.umur
            )
        )
    }

    fun mapPredictDomainToResponse(predict: Predict?): List<Instance?> {
        val instanceList = ArrayList<Instance?>()
        val instance = predict?.let {
            Instance(
                gaji = it.gaji,
                pekerjaan = it.pekerjaan,
                tanggungan = it.tanggungan,
                umur = it.umur
            )
        }
        instanceList.add(instance)
        return instanceList
    }

    fun mapPredictResponseToPredictDomain(predictions: List<List<Double>>?): Flow<ResultPredict> {
        return flowOf(
            ResultPredict(predictions = predictions)
        )
    }

    fun mapResponseTrackingToDomain(data: List<TrackingRemote>?): Flow<List<Tracking>> {
        val list = ArrayList<Tracking>()
        data?.map {
            val tracking = Tracking(
                id = it.id,
                nik_penerima = it.nik_penerima,
                alamat = it.alamat,
                status = it.status
            )
            list.add(tracking)
        }
        return flowOf(list)
    }

    fun mapResponseDetailTrackingToDomain(data: List<DetailTrackingRemote>?): Flow<List<DetailTracking>> {

        val list = ArrayList<DetailTracking>()
        data?.map {
            val detailTracking =
                DetailTracking(id_track = it.id_track, lokasi = it.lokasi, waktu = it.waktu)
            list.add(detailTracking)
        }
        return flowOf(list)
    }
}