package com.example.universityextracurricular.model

import com.google.gson.annotations.SerializedName

data class ExtracurricularClassesRegistration(
    @SerializedName("id") val id: Long,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("edad") val edad: Int,
    @SerializedName("deporte") val deporte: Deporte
)
