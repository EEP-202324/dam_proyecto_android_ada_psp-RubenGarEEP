package com.example.universityextracurricular.network

import com.example.universityextracurricular.model.ExtracurricularClassesRegistration
import com.example.universityextracurricular.model.Deporte
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("registrations")
    fun getRegistrations(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String
    ): Call<List<ExtracurricularClassesRegistration>>

    @POST("registrations")
    fun createRegistration(@Body registration: ExtracurricularClassesRegistration): Call<ExtracurricularClassesRegistration>

    @GET("registrations/{id}")
    fun getRegistrationById(@Path("id") id: Long): Call<ExtracurricularClassesRegistration>

    @PUT("registrations/{id}")
    fun updateRegistration(
        @Path("id") id: Long,
        @Body registration: ExtracurricularClassesRegistration
    ): Call<ExtracurricularClassesRegistration>

    @DELETE("registrations/{id}")
    fun deleteRegistration(@Path("id") id: Long): Call<Void>

    @GET("default-values/deportes")
    fun getDeportes(): Call<List<Deporte>>
}
