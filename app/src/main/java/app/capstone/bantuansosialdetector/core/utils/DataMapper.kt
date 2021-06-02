package app.capstone.bantuansosialdetector.core.utils

import app.capstone.bantuansosialdetector.core.data.source.remote.response.Instance
import app.capstone.bantuansosialdetector.core.data.source.remote.response.RecipientRemote
import app.capstone.bantuansosialdetector.core.domain.model.Predict
import app.capstone.bantuansosialdetector.core.domain.model.Recipient
import app.capstone.bantuansosialdetector.core.domain.model.ResultPredict
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {
//    fun mapResponsesToDomain(input: RecipientRemote) =
//        Recipient(
//            id = input.id,
//            nama = input.nama,
//            alamat = input.alamat,
//            gaji = input.gaji,
//            pekerjaan = input.pekerjaan,
//            tanggungan = input.tanggungan,
//            umur = input.umur,
//            status = input.status
//        )

    fun mapDomainToResponse(input: Recipient) =
        RecipientRemote(
            id = input.id,
            nik = input.noNik,
            nama = input.nama,
            no_hp = input.noHp,
            alamat = input.alamat,
            gaji = input.gaji,
            pekerjaan = input.pekerjaan,
            tanggungan = input.tanggungan,
            umur = input.umur,
            status = input.status
        )

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

    fun mapPredictDomainToResponse(predict: Predict): List<Instance> {
        val instanceList = ArrayList<Instance>()
         val instance =Instance(
            gaji = predict.gaji,
            pekerjaan = predict.pekerjaan,
            tanggungan = predict.tanggungan,
            umur = predict.umur
        )
        instanceList.add(instance)
        return instanceList
    }

    fun mapPredictResponseToPredictDomain(predictions: List<List<Double>>?): Flow<ResultPredict> {
        return flowOf(
            ResultPredict(predictions = predictions)
        )
    }
}