package app.capstone.bantuansosialdetector.core.data.source

import app.capstone.bantuansosialdetector.core.data.source.remote.RemoteDataSource
import app.capstone.bantuansosialdetector.core.data.source.remote.network.ApiResponse
import app.capstone.bantuansosialdetector.core.data.source.remote.response.RecipientRemote
import app.capstone.bantuansosialdetector.core.data.source.remote.response.RecipientResponse
import app.capstone.bantuansosialdetector.core.domain.model.Recipient
import app.capstone.bantuansosialdetector.core.domain.repository.IBanSosRepository
import app.capstone.bantuansosialdetector.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow

class BanSosRepository(private val remoteDataSource: RemoteDataSource) : IBanSosRepository {

    override fun insertRecipient(recipient: Recipient) =
        object : NetworkBoundResource<Recipient, RecipientResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<RecipientResponse>> {
                val recipientRemote = DataMapper.mapDomainToResponse(recipient)
                return remoteDataSource.insertRecipient(recipientRemote)
            }

            override suspend fun fetchFromNetwork(data: RecipientResponse): Flow<Recipient> {
                return DataMapper.mapResponsesToDomainWithFlow(data)
            }
        }.asFlow()


    override fun getRecipientById(): Flow<Resource<RecipientRemote>> {
        TODO("Not yet implemented")
    }

    override fun updateRecipient(recipientRemote: RecipientRemote, status: Boolean) {
        TODO("Not yet implemented")
    }
}