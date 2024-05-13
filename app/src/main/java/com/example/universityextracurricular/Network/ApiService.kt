package com.example.universityextracurricular

import ExtracurricularClassesRegistration
import com.example.universityextracurricular.model.Deporte
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("default-values/deportes")
    fun getDeportes(): Call<List<Deporte>>

    @POST("registrations")
    fun createRegistration(@Body registration: ExtracurricularClassesRegistration): Call<ExtracurricularClassesRegistration>
}
