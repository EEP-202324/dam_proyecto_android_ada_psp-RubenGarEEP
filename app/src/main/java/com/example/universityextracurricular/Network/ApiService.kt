package com.example.universityextracurricular

import com.example.universityextracurricular.model.Deporte
import com.example.universityextracurricular.model.ExtracurricularClassesRegistration
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("api/deportes")
    fun getDeportes(): Call<List<Deporte>>

    @POST("api/registrations")
    fun createRegistration(@Body registration: ExtracurricularClassesRegistration): Call<ExtracurricularClassesRegistration>
}
