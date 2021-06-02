package app.capstone.bantuansosialdetector.core.domain.usecase

import app.capstone.bantuansosialdetector.core.data.source.Resource
import app.capstone.bantuansosialdetector.core.domain.model.Recipient
import kotlinx.coroutines.flow.Flow

interface BanSosUseCase {
    fun insertRecipient(recipient: Recipient): Flow<Resource<Recipient>>
    fun getRecipient(): Flow<Recipient>
    fun updateRecipient(recipient: Recipient, status: Boolean)
}
