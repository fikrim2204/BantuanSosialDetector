package app.capstone.bantuansosialdetector.core.domain.repository

import app.capstone.bantuansosialdetector.core.data.source.Resource
import app.capstone.bantuansosialdetector.core.data.source.remote.response.RecipientRemote
import app.capstone.bantuansosialdetector.core.domain.model.Recipient
import kotlinx.coroutines.flow.Flow

interface IBanSosRepository {
    fun insertRecipient(recipient: Recipient): Flow<Resource<Recipient>>
    fun getRecipientById(): Flow<Resource<RecipientRemote>>
    fun updateRecipient(recipientRemote: RecipientRemote, status: Boolean)
}