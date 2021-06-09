package app.capstone.bantuansosialdetector.core.data.source.remote.network

import app.capstone.bantuansosialdetector.core.data.source.remote.response.PredictResponse
import app.capstone.bantuansosialdetector.core.data.source.remote.response.ResultPredictResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService2 {
    @POST("/v1/models/bsd:predict")
    suspend fun postPredict(@Body predictResponse: PredictResponse): ResultPredictResponse
}