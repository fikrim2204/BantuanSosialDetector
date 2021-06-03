package app.capstone.bantuansosialdetector.core.data.source.remote.network

import app.capstone.bantuansosialdetector.core.data.source.remote.response.DetailTrackingResponse
import app.capstone.bantuansosialdetector.core.data.source.remote.response.InsertResponse
import app.capstone.bantuansosialdetector.core.data.source.remote.response.RecipientResponse
import app.capstone.bantuansosialdetector.core.data.source.remote.response.TrackingResponse
import retrofit2.http.*

interface ApiService {
    @GET("/api/v1/penerima/{nik}")
    suspend fun getRecipientById(
        @Path("nik") nik: String?
    ): RecipientResponse

    @GET("/api/v1/tracking/{nik}/getTrack")
    suspend fun getTrackingByNik(
        @Path("nik") nik: String?
    ) : TrackingResponse

    @GET("/api/v1/tracking/{id}/getDetailTrack")
    suspend fun getTrackingDetailTracking(
        @Path("id") id: String?
    ) : DetailTrackingResponse

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

    @FormUrlEncoded
    @PUT("/api/v1/penerima/update/{id}")
    suspend fun putRecipientById(
        @Path("id") id: String?,
        @Field("status") status: Int?
    ): InsertResponse
}