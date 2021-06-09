package app.capstone.bansosadmin.data.source.remote

import app.capstone.bansosadmin.data.source.remote.network.ApiInterface
import app.capstone.bansosadmin.data.source.remote.network.Responses
import app.capstone.bansosadmin.data.source.remote.response.*
import app.capstone.bansosadmin.domain.model.Insert
import app.capstone.bansosadmin.domain.model.UpdateItemTrack
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class RemoteDataSource(private val apiInterface: ApiInterface) {
    fun getRecipientById(id: String): Flow<Responses<List<Recepient>>> {
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

    fun insertTrack(insert: Insert): Flow<Responses<List<InsertDeleteTrack>>> {
        return flow {
            try {
                val responses =
                    apiInterface.insertTracking(insert.nik_penerima, insert.alamat!!)
                if (responses.success) {
                    emit(Responses.Success(listOf(responses.data)))
                } else {
                    emit(Responses.Empty)
                }
            } catch (e: Exception) {
                emit(Responses.Error(e.toString()))
            }
        }.flowOn(IO)
    }

    fun finishTrack(insert: Insert): Flow<Responses<InsertDeleteTrack>> {
        return flow {
            try {
                val responses =
                    apiInterface.updateTrackStatus(
                        insert.nik_penerima,
                        insert.id.toString()
                    )
                if (responses.success) {
                    emit(Responses.Success(responses.data))
                } else {
                    emit(Responses.Empty)
                }
            } catch (e: Exception) {
                emit(Responses.Error(e.toString()))
            }
        }.flowOn(IO)
    }

    fun deleteTrack(id: String): Flow<Responses<List<InsertDeleteTrack>>> {
        return flow {
            try {
                val responses =
                    apiInterface.deleteTrackById(id)
                if (responses.success) {
                    emit(Responses.Success(listOf(responses.data)))
                } else {
                    emit(Responses.Empty)
                }
            } catch (e: Exception) {
                emit(Responses.Error(e.toString()))
            }
        }.flowOn(IO)
    }

    fun updateTrack(
        updateItemTrack: UpdateItemTrack,
        id: String
    ): Flow<Responses<UpdateTrack>> {
        return flow {
            try {
                val responses =
                    apiInterface.updateTrack(id, updateItemTrack.lokasi, updateItemTrack.waktu)
                if (responses.success) {
                    emit(Responses.Success(responses.data))
                } else {
                    emit(Responses.Empty)
                }
            } catch (e: Exception) {
                emit(Responses.Error(e.toString()))
            }
        }.flowOn(IO)
    }

    fun getTrack(id: String): Flow<Responses<List<TrackItems>>> {
        return flow {
            try {
                val response = apiInterface.getTrackById(id)
                val dataArray = response.data
                emit(Responses.Success(dataArray))
            } catch (e: Exception) {
                emit(Responses.Error(e.toString()))
            }
        }.flowOn(IO)
    }

    fun getTracksDetail(id: String): Flow<Responses<List<DetailTrackItems>>> {
        return flow {
            try {
                val response = apiInterface.getTracksDetail(id)
                val dataArray = response.data
                emit(Responses.Success(dataArray))
            } catch (e: Exception) {
                emit(Responses.Error(e.toString()))
            }
        }.flowOn(IO)
    }

}

