package app.capstone.bansosadmin.data.source.remote.network

import app.capstone.bansosadmin.data.source.remote.response.RecepientResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("api/v1/penerima/{id}")
    suspend fun getRecepientById(@Path("id") id: String): RecepientResponse
}