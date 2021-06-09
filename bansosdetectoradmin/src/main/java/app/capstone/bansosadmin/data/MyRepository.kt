package app.capstone.bansosadmin.data

import app.capstone.bansosadmin.data.source.remote.RemoteDataSource
import app.capstone.bansosadmin.data.source.remote.network.Responses
import app.capstone.bansosadmin.data.source.remote.response.*
import app.capstone.bansosadmin.domain.model.*
import app.capstone.bansosadmin.domain.repository.IMyRepository
import app.capstone.bansosadmin.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MyRepository(private val remoteDataSource: RemoteDataSource) : IMyRepository {
    override fun getRecipientById(id: String): Flow<Resource<List<Recipients>>> =
        object : NetworkBoundResource<List<Recipients>, List<Recepient>>() {
            override fun fetchFromNetwork(type: List<Recepient>) =
                DataMapper.mapResponsesToDomain(type)

            override suspend fun createCall(): Flow<Responses<List<Recepient>>> =
                remoteDataSource.getRecipientById(id)


        }.asFlow()

    override fun insertTrack(insert: Insert): Flow<Resource<List<Insert>>> =
        object : NetworkBoundResource<List<Insert>, List<InsertDeleteTrack>>() {
            override fun fetchFromNetwork(type: List<InsertDeleteTrack>): Flow<List<Insert>> =
                DataMapper.mapResponsesInsertToDomain(type)

            override suspend fun createCall(): Flow<Responses<List<InsertDeleteTrack>>> =
                remoteDataSource.insertTrack(insert)
        }.asFlow()

    override fun finishTrack(insert: Insert): Flow<Resource<Insert>> =
        object : NetworkBoundResource<Insert, InsertDeleteTrack>() {
            override fun fetchFromNetwork(type: InsertDeleteTrack): Flow<Insert> =
                DataMapper.mapResponseFinishToDomain(type)

            override suspend fun createCall(): Flow<Responses<InsertDeleteTrack>> =
                remoteDataSource.finishTrack(insert)

        }.asFlow()

    override fun deleteTrack(id: String): Flow<Resource<List<Insert>>> =
        object : NetworkBoundResource<List<Insert>, List<InsertDeleteTrack>>() {
            override fun fetchFromNetwork(type: List<InsertDeleteTrack>) =
                DataMapper.mapResponsesInsertToDomain(type)

            override suspend fun createCall(): Flow<Responses<List<InsertDeleteTrack>>> =
                remoteDataSource.deleteTrack(id)

        }.asFlow()

    override fun updateTrack(
        id: String,
        updateItemTrack: UpdateItemTrack
    ): Flow<Resource<UpdateItemTrack>> =
        object : NetworkBoundResource<UpdateItemTrack, UpdateTrack>() {
            override fun fetchFromNetwork(type: UpdateTrack): Flow<UpdateItemTrack> =
                flowOf(UpdateItemTrack(type.lokasi, type.waktu))

            override suspend fun createCall(): Flow<Responses<UpdateTrack>> =
                remoteDataSource.updateTrack(updateItemTrack, id)

        }.asFlow()


    override fun getTrackById(id: String): Flow<Resource<List<Tracks>>> =
        object : NetworkBoundResource<List<Tracks>, List<TrackItems>>() {
            override fun fetchFromNetwork(type: List<TrackItems>): Flow<List<Tracks>> =
                DataMapper.mapResponsesTrackToDomain(type)

            override suspend fun createCall(): Flow<Responses<List<TrackItems>>> =
                remoteDataSource.getTrack(id)
        }.asFlow()

    override fun getTrackDetailById(id: String): Flow<Resource<List<DetailTracks>>> =
        object : NetworkBoundResource<List<DetailTracks>, List<DetailTrackItems>>() {
            override fun fetchFromNetwork(type: List<DetailTrackItems>): Flow<List<DetailTracks>> =
                DataMapper.mapResponsesDetailToDomain(type)

            override suspend fun createCall(): Flow<Responses<List<DetailTrackItems>>> =
                remoteDataSource.getTracksDetail(id)

        }.asFlow()

}