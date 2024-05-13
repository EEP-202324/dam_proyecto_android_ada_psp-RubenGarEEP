package com.example.universityextracurricular.model

data class ExtracurricularClassesRegistration(
    val id: Long,
    val nombre: String,
    val edad: Int,
    val deporte: Deporte
)
