package com.example.universityextracurricular.ui

import ExtracurricularClassesRegistration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.universityextracurricular.ApiService
import com.example.universityextracurricular.model.Deporte
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RegistrationScreen(apiService: ApiService) {
    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var deporteNombre by remember { mutableStateOf("") }
    var registroResultado by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        TextField(
            value = edad,
            onValueChange = { edad = it },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        TextField(
            value = deporteNombre,
            onValueChange = { deporteNombre = it },
            label = { Text("Nombre del Deporte") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        Button(
            onClick = {
                if (nombre.isNotEmpty() && edad.isNotEmpty() && deporteNombre.isNotEmpty()) {
                    registerAlumno(apiService, nombre, edad.toInt(), deporteNombre) {
                        registroResultado = "Registro exitoso"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Registrarse")
        }
        if (registroResultado.isNotEmpty()) {
            Text(registroResultado, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
        }
    }
}

fun registerAlumno(apiService: ApiService, nombre: String, edad: Int, deporteNombre: String, onSuccess: () -> Unit) {
    apiService.getDeportes().enqueue(object : Callback<List<Deporte>> {
        override fun onResponse(call: Call<List<Deporte>>, response: Response<List<Deporte>>) {
            if (response.isSuccessful) {
                val deportes = response.body() ?: emptyList()
                val deporte = deportes.find { it.nombre.equals(deporteNombre, ignoreCase = true) }
                if (deporte != null) {
                    val registro = ExtracurricularClassesRegistration(
                        nombre = nombre,
                        edad = edad,
                        deporte = deporte
                    )
                    apiService.createRegistration(registro).enqueue(object : Callback<ExtracurricularClassesRegistration> {
                        override fun onResponse(
                            call: Call<ExtracurricularClassesRegistration>,
                            response: Response<ExtracurricularClassesRegistration>
                        ) {
                            if (response.isSuccessful) {
                                onSuccess()
                            }
                        }

                        override fun onFailure(call: Call<ExtracurricularClassesRegistration>, t: Throwable) {
                            // Handle failure
                        }
                    })
                }
            }
        }

        override fun onFailure(call: Call<List<Deporte>>, t: Throwable) {
            // Handle failure
        }
    })
}
