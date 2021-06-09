package app.capstone.bantuansosialdetector.core.data.source

import app.capstone.bantuansosialdetector.core.data.source.remote.RemoteDataSource
import app.capstone.bantuansosialdetector.core.data.source.remote.network.ApiResponse
import app.capstone.bantuansosialdetector.core.data.source.remote.response.*
import app.capstone.bantuansosialdetector.core.domain.model.*
import app.capstone.bantuansosialdetector.core.domain.repository.IBanSosRepository
import app.capstone.bantuansosialdetector.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow

class BanSosRepository(private val remoteDataSource: RemoteDataSource) : IBanSosRepository {

    override fun insertRecipient(recipient: Recipient) =
        object : NetworkBoundResource<Insert, InsertResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<InsertResponse>> {
                return remoteDataSource.insertRecipient(recipient)
            }

            override suspend fun fetchFromNetwork(data: InsertResponse): Flow<Insert> {
                return DataMapper.mapResponsesInsertToDomain(data)
            }
        }.asFlow()


    override fun getRecipientByNik(nik: String?): Flow<Resource<List<Recipient>>> =
        object : NetworkBoundResource<List<Recipient>, RecipientResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<RecipientResponse>> {
                return remoteDataSource.getRecipientByNik(nik)
            }

            override suspend fun fetchFromNetwork(data: RecipientResponse): Flow<List<Recipient>> {
                return DataMapper.mapResponsesToDomainWithFlow(data.data)
            }
        }.asFlow()

    override fun updateRecipient(id: String?, status: Int?) =
        object : NetworkBoundResource<Insert, InsertResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<InsertResponse>> {
                return remoteDataSource.putRecipientById(id, status)
            }

            override suspend fun fetchFromNetwork(data: InsertResponse): Flow<Insert> {
                return DataMapper.mapResponsesInsertToDomain(data)
            }
        }.asFlow()

    override fun getTracking(nik: String?): Flow<Resource<List<Tracking>>> =
        object : NetworkBoundResource<List<Tracking>, TrackingResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<TrackingResponse>> {
                return remoteDataSource.getTracking(nik)
            }

            override suspend fun fetchFromNetwork(data: TrackingResponse): Flow<List<Tracking>> {
                return DataMapper.mapResponseTrackingToDomain(data.data)
            }
        }.asFlow()

    override fun getDetailTracking(id: String?): Flow<Resource<List<DetailTracking>>> =
        object : NetworkBoundResource<List<DetailTracking>, DetailTrackingResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<DetailTrackingResponse>> {
                return remoteDataSource.getDetailTracking(id)
            }

            override suspend fun fetchFromNetwork(data: DetailTrackingResponse): Flow<List<DetailTracking>> {
                return DataMapper.mapResponseDetailTrackingToDomain(data.data)
            }
        }.asFlow()

    override fun postPredict(predict: Predict?): Flow<Resource<ResultPredict>> =
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