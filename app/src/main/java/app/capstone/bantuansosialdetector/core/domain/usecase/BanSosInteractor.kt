package app.capstone.bantuansosialdetector.core.domain.usecase

import app.capstone.bantuansosialdetector.core.data.source.Resource
import app.capstone.bantuansosialdetector.core.domain.model.Recipient
import app.capstone.bantuansosialdetector.core.domain.repository.IBanSosRepository
import kotlinx.coroutines.flow.Flow

class BanSosInteractor(private val banSosRepository: IBanSosRepository) :
    BanSosUseCase {
    override fun insertRecipient(recipient: Recipient): Flow<Resource<Recipient>> = banSosRepository.insertRecipient(recipient)

    override fun getRecipient(): Flow<Recipient> {
        TODO("Not yet implemented")
    }

    override fun updateRecipient(recipient: Recipient, status: Boolean) {
        TODO("Not yet implemented")
    }
}