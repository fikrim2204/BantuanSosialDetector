package app.capstone.bantuansosialdetector.core.domain.usecase

import app.capstone.bantuansosialdetector.core.data.source.Resource
import app.capstone.bantuansosialdetector.core.domain.model.Predict
import app.capstone.bantuansosialdetector.core.domain.model.Recipient
import app.capstone.bantuansosialdetector.core.domain.model.ResultPredict
import app.capstone.bantuansosialdetector.core.domain.repository.IBanSosRepository
import kotlinx.coroutines.flow.Flow

class BanSosInteractor(private val banSosRepository: IBanSosRepository) :
    BanSosUseCase {
    override fun insertRecipient(recipient: Recipient) = banSosRepository.insertRecipient(recipient)

    override fun getRecipientByNik(nik: String?): Flow<Resource<List<Recipient>>> =
        banSosRepository.getRecipientByNik(nik)

    override fun updateRecipient(id: String?, status: Int?) =
        banSosRepository.updateRecipient(id, status)

    override fun getTracking(nik: String?) = banSosRepository.getTracking(nik)

    override fun getDetailTracking(id: String) = banSosRepository.getDetailTracking(id)

    override fun postPredict(predict: Predict?): Flow<Resource<ResultPredict>> =
        banSosRepository.postPredict(predict)
}