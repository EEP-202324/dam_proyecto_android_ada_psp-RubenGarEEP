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
    var successMessage by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    fun registerAlumno() {
        if (nombre.isNotEmpty() && edad.isNotEmpty() && deporteNombre.isNotEmpty()) {
            val registro = ExtracurricularClassesRegistration(
                nombre = nombre,
                edad = edad.toInt(),
                deporte = Deporte(id = null, nombre = deporteNombre)
            )

            apiService.createRegistration(registro).enqueue(object : Callback<ExtracurricularClassesRegistration> {
                override fun onResponse(call: Call<ExtracurricularClassesRegistration>, response: Response<ExtracurricularClassesRegistration>) {
                    if (response.isSuccessful) {
                        successMessage = "Registro exitoso"
                        errorMessage = ""
                    } else {
                        successMessage = ""
                        errorMessage = "Error: ${response.code()} - ${response.message()}"
                    }
                }

                override fun onFailure(call: Call<ExtracurricularClassesRegistration>, t: Throwable) {
                    successMessage = ""
                    errorMessage = "Error: ${t.message}"
                }
            })
        } else {
            errorMessage = "Por favor, completa todos los campos"
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Registrar Alumno",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = edad,
            onValueChange = { edad = it },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = deporteNombre,
            onValueChange = { deporteNombre = it },
            label = { Text("Nombre del Deporte") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        Button(
            onClick = { registerAlumno() },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Registrarse")
        }

        if (successMessage.isNotEmpty()) {
            Text(successMessage, color = MaterialTheme.colorScheme.primary)
        }

        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
        }
    }
}
