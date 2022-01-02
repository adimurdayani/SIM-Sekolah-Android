package com.example.simsekolah.core.data.source

import com.example.simsekolah.core.data.model.ResponsModel
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("user/register")
    fun register(
        @Field("email") email: String,
        @Field("role") role: String,
        @Field("nomer") nomer: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String
    ): Call<ResponsModel>

    @FormUrlEncoded
    @POST("user/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponsModel>

    @GET("guru/idcard/{id_card}")
    fun id_card(
        @Path("id_card") id_card: String
    ): Call<ResponsModel>

    @GET("guru/{mapel_id}")
    fun guru_mapel(
        @Path("mapel_id") mapel_id: Int
    ): Call<ResponsModel>

    @GET("siswa/{no_induk}")
    fun no_induk(
        @Path("no_induk") no_induk: String
    ): Call<ResponsModel>

    @GET("siswa/{kelas_id}")
    fun kelas_id(
        @Path("kelas_id") kelas_id: Int
    ): Call<ResponsModel>

    @GET("siswa/kelas/{kelas_id}")
    fun siswa_kelas_id(
        @Path("kelas_id") kelas_id: Int
    ): Call<ResponsModel>

    @GET("kelas")
    fun kelas(
    ): Call<ResponsModel>

    @GET("mapel")
    fun mapel(
    ): Call<ResponsModel>

    @GET("nilai/{kelas_id}")
    fun nilai(
        @Path("kelas_id") kelas_id: Int
    ): Call<ResponsModel>

    @GET("nilai/ulangan/{mapel_id}")
    fun ulangan(
        @Path("mapel_id") mapel_id: Int
    ): Call<ResponsModel>

    @GET("jadwal/{kelas_id}")
    fun jadwal(
        @Path("kelas_id") kelas_id: Int
    ): Call<ResponsModel>
}