package com.example.universityextracurricular

import ExtracurricularClassesRegistration
import com.example.universityextracurricular.model.Deporte
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("/api/default-values/deportes")
    fun getDeportes(): Call<List<Deporte>>

    @POST("/api/registrations")
    fun createRegistration(@Body registration: ExtracurricularClassesRegistration): Call<ExtracurricularClassesRegistration>

    @DELETE("api/registrations")
    fun deleteRegistrationByNameAndDeporte(
        @Query("nombre") nombre: String,
        @Query("deporteNombre") deporteNombre: String
    ): Call<Void>

    @GET("/api/default-values/deporte")
    fun getDeporteByName(@Query("nombre") nombre: String): Call<Deporte>

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8080" // URL del servidor

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
