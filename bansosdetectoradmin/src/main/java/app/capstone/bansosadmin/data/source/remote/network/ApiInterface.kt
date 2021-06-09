package app.capstone.bansosadmin.data.source.remote.network

import app.capstone.bansosadmin.data.source.remote.response.*
import retrofit2.http.*

interface ApiInterface {
    @GET("api/v1/penerima/{id}")
    suspend fun getRecepientById(@Path("id") id: String): RecepientResponse

    @FormUrlEncoded
    @POST("api/v1/tracking/{id}/updateTrack")
    suspend fun updateTrack(
        @Path("id") id: String,
        @Field("lokasi") lokasi: String,
        @Field("waktu") waktu: String
    ): UpdateInsertResponse

    @GET("api/v1/tracking/{id}/getTrack")
    suspend fun getTrackById(@Path("id") id: String): TrackResponse

    @FormUrlEncoded
    @POST("api/v1/tracking/{id}/createTrack")
    suspend fun insertTracking(
        @Path("id") id: String,
        @Field("alamat") alamat: String,
        @Field("status") status: String? = "On Process"
    ): InsertDeleteTrackResponse

    @DELETE("api/v1/tracking/{id_track}/deleteTrack")
    suspend fun deleteTrackById(@Path("id_track") idTrack: String): InsertDeleteTrackResponse

    @GET("api/v1/tracking/{id_track}/getDetailTrack")
    suspend fun getTracksDetail(@Path("id_track") id: String): DetailTrackResponse

    @FormUrlEncoded
    @PUT("api/v1/tracking/{nik}/{id_track}/finishTrack")
    suspend fun updateTrackStatus(
        @Path("nik") nik: String,
        @Path("id_track") idTrack: String,
        @Field("status") status: String? = "Selesai"
    ): InsertDeleteTrackResponse
}