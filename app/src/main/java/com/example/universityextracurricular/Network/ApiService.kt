package com.example.universityextracurricular

import ExtracurricularClassesRegistration
import com.example.universityextracurricular.model.Deporte
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("deportes")
    fun getDeportes(): Call<List<Deporte>>

    @POST("api/registrations")
    fun createRegistration(@Body registration: ExtracurricularClassesRegistration): Call<ExtracurricularClassesRegistration>

    companion object {
        fun create(): Any {
            TODO("Not yet implemented")
        }
    }
}
