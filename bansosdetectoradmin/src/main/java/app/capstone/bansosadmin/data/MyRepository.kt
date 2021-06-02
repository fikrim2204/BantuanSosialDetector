package app.capstone.bansosadmin.data

import app.capstone.bansosadmin.data.source.remote.RemoteDataSource
import app.capstone.bansosadmin.data.source.remote.network.Responses
import app.capstone.bansosadmin.data.source.remote.response.Recepient
import app.capstone.bansosadmin.domain.model.Penerima
import app.capstone.bansosadmin.domain.repository.IMyRepository
import app.capstone.bansosadmin.utils.DataMapper
import kotlinx.coroutines.flow.Flow

class MyRepository(private val remoteDataSource: RemoteDataSource) : IMyRepository {
    override fun getPenerimaById(id: String): Flow<Resource<List<Penerima>>> =
        object : NetworkBoundResource<List<Penerima>, List<Recepient>>() {
            override fun fetchFromNetwork(type: List<Recepient>) =
                DataMapper.mapResponsesToDomain(type)

            override suspend fun createCall(): Flow<Responses<List<Recepient>>> =
                remoteDataSource.getPenerima(id)


        }.asFlow()

}