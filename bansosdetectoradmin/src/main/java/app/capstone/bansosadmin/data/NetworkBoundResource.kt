package app.capstone.bansosadmin.data

import app.capstone.bansosadmin.data.source.remote.network.Responses
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {
    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = createCall().first()) {
            is Responses.Success -> {
                emitAll(fetchFromNetwork(apiResponse.data).map {
                    Resource.Success(it)
                })
            }
            is Responses.Error -> {
                onFetchFailed()
                emit(
                    Resource.Error<ResultType>(
                        apiResponse.errorMessage
                    )
                )
            }
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun fetchFromNetwork(type: RequestType): Flow<ResultType>

    protected abstract suspend fun createCall(): Flow<Responses<RequestType>>

    fun asFlow(): Flow<Resource<ResultType>> = result
}