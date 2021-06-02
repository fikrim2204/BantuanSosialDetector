package app.capstone.bantuansosialdetector.core.data.source.remote

import android.util.Log
import app.capstone.bantuansosialdetector.core.data.source.remote.network.ApiResponse
import app.capstone.bantuansosialdetector.core.data.source.remote.network.ApiService
import app.capstone.bantuansosialdetector.core.data.source.remote.response.PredictResponse
import app.capstone.bantuansosialdetector.core.data.source.remote.response.RecipientRemote
import app.capstone.bantuansosialdetector.core.data.source.remote.response.RecipientResponse
import app.capstone.bantuansosialdetector.core.data.source.remote.response.ResultPredictResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    fun insertRecipient(recipient: RecipientRemote): Flow<ApiResponse<RecipientResponse>> {
        return flow {
            try {
                val response = apiService.insertRecipient(
                    recipient.nik,
                    recipient.nama,
                    recipient.no_hp,
                    recipient.alamat,
                    recipient.gaji,
                    recipient.pekerjaan,
                    recipient.tanggungan,
                    recipient.umur,
                    recipient.status
                )
                if (response.success) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getRecipientByNik(nik: String): Flow<ApiResponse<RecipientResponse>> {
        return flow {
            try {
                val response = apiService.getRecipientById(nik)
                if (response.success) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun postPredict(predictResponse: PredictResponse): Flow<ApiResponse<ResultPredictResponse>> {
        return flow {
            try {
                val response = apiService.postPredict(predictResponse)

                if (response != null) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.d("RemoteDataSource:", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}