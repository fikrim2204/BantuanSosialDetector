package app.capstone.bantuansosialdetector.core.data.source.remote.network

import app.capstone.bantuansosialdetector.core.data.source.remote.response.InsertResponse
import app.capstone.bantuansosialdetector.core.data.source.remote.response.PredictResponse
import app.capstone.bantuansosialdetector.core.data.source.remote.response.RecipientResponse
import app.capstone.bantuansosialdetector.core.data.source.remote.response.ResultPredictResponse
import retrofit2.http.*

interface ApiService {
    @GET("/api/v1/penerima/{nik}")
    suspend fun getRecipientById(
        @Path("nik") nik: String
    ): RecipientResponse

    @FormUrlEncoded
    @POST("/api/v1/penerima/insert")
    suspend fun insertRecipient(
        @Field("nik") nik: Long?,
        @Field("nama") name: String?,
        @Field("no_hp") no_hp: Long?,
        @Field("alamat") address: String?,
        @Field("gaji") salary: Double?,
        @Field("pekerjaan") job: Int?,
        @Field("tanggungan") dependents: Int?,
        @Field("umur") age: Int?,
        @Field("status") status: Int?
    ): InsertResponse

    @POST(":8501/v1/models/bsd:predictResponse")
    suspend fun postPredict(@Body predictResponse: PredictResponse): ResultPredictResponse
}