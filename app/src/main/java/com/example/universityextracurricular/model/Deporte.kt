package com.example.universityextracurricular.model

import com.google.gson.annotations.SerializedName

data class Deporte(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("nombre")
    val nombre: String
)
