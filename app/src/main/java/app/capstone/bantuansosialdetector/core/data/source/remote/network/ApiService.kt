package app.capstone.bantuansosialdetector.core.data.source.remote.network

import app.capstone.bantuansosialdetector.core.data.source.remote.response.RecipientResponse
import retrofit2.http.*

interface ApiService {
    @GET("api/v1/penerima/{id}")
    suspend fun getRecipientById(
        @Path("id") id: String
    ) : RecipientResponse

    @FormUrlEncoded
    @POST("api/v1/penerima/insert")
    suspend fun insertRecipient(
        @Field("nama") name: String?,
        @Field("alamat") address: String?,
        @Field("gaji") salary: Int?,
        @Field("pekerjaan") job: String?,
        @Field("tanggungan") dependents: Int?,
        @Field("umur") age: Int?,
        @Field("status") status: Boolean?
    ) : RecipientResponse
}