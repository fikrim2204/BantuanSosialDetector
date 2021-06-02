package app.capstone.bansosadmin.data.source.remote

import app.capstone.bansosadmin.data.source.remote.network.ApiInterface
import app.capstone.bansosadmin.data.source.remote.network.Responses
import app.capstone.bansosadmin.data.source.remote.response.Recepient
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class RemoteDataSource(private val apiInterface: ApiInterface) {
    fun getPenerima(id: String): Flow<Responses<List<Recepient>>> {
        return flow {
            try {
                val response = apiInterface.getRecepientById(id)
                val dataArray = response.data
                if (dataArray.isNotEmpty())
                    emit(Responses.Success(dataArray))
                else
                    emit(Responses.Empty)

            } catch (e: Exception) {
                emit(Responses.Error(e.toString()))
            }
        }.flowOn(IO)
    }
}

