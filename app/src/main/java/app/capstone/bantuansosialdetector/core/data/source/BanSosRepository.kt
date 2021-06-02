package app.capstone.bantuansosialdetector.core.data.source

import app.capstone.bantuansosialdetector.core.data.source.remote.RemoteDataSource
import app.capstone.bantuansosialdetector.core.data.source.remote.network.ApiResponse
import app.capstone.bantuansosialdetector.core.data.source.remote.response.PredictResponse
import app.capstone.bantuansosialdetector.core.data.source.remote.response.RecipientRemote
import app.capstone.bantuansosialdetector.core.data.source.remote.response.RecipientResponse
import app.capstone.bantuansosialdetector.core.data.source.remote.response.ResultPredictResponse
import app.capstone.bantuansosialdetector.core.domain.model.Predict
import app.capstone.bantuansosialdetector.core.domain.model.Recipient
import app.capstone.bantuansosialdetector.core.domain.model.ResultPredict
import app.capstone.bantuansosialdetector.core.domain.repository.IBanSosRepository
import app.capstone.bantuansosialdetector.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow

class BanSosRepository(private val remoteDataSource: RemoteDataSource) : IBanSosRepository {

    override fun insertRecipient(recipient: Recipient) =
        object : NetworkBoundResource<List<Recipient>, RecipientResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<RecipientResponse>> {
                val recipientRemote = DataMapper.mapDomainToResponse(recipient)
                return remoteDataSource.insertRecipient(recipientRemote)
            }

            override suspend fun fetchFromNetwork(data: RecipientResponse): Flow<List<Recipient>> {
                return DataMapper.mapResponsesToDomainWithFlow(data.data)
            }
        }.asFlow()


    override fun getRecipientByNik(nik: String): Flow<Resource<List<Recipient>>> =
        object : NetworkBoundResource<List<Recipient>, RecipientResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<RecipientResponse>> {
                return remoteDataSource.getRecipientByNik(nik)
            }

            override suspend fun fetchFromNetwork(data: RecipientResponse): Flow<List<Recipient>> {
                return DataMapper.mapResponsesToDomainWithFlow(data.data)
            }
        }.asFlow()

    override fun updateRecipient(recipientRemote: RecipientRemote, status: Boolean) {
        TODO("Not yet implemented")
    }

    override fun postPredict(predict: Predict): Flow<Resource<ResultPredict>> =
        object : NetworkBoundResource<ResultPredict, ResultPredictResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<ResultPredictResponse>> {
                val instance = DataMapper.mapPredictDomainToResponse(predict)
                val predictRemote =
                    PredictResponse(signature_name = "serving_default", instances = instance)
                return remoteDataSource.postPredict(predictRemote)
            }

            override suspend fun fetchFromNetwork(data: ResultPredictResponse): Flow<ResultPredict> {
                return DataMapper.mapPredictResponseToPredictDomain(data.predictions)
            }
        }.asFlow()
}