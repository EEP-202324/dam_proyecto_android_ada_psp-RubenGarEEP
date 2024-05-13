package com.example.universityextracurricular

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.universityextracurricular.model.Deporte
import com.example.universityextracurricular.model.ExtracurricularClassesRegistration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RegistrationScreen(apiService: ApiService) {
    var nombre by remember { mutableStateOf(TextFieldValue("")) }
    var edad by remember { mutableStateOf(TextFieldValue("")) }
    var deporteId by remember { mutableStateOf(TextFieldValue("")) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Registro", style = MaterialTheme.typography.headlineLarge)

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = edad,
            onValueChange = { edad = it },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = deporteId,
            onValueChange = { deporteId = it },
            label = { Text("ID del Deporte") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        Button(
            onClick = {
                val registration = ExtracurricularClassesRegistration(
                    id = 0, // Será generado por el backend
                    nombre = nombre.text,
                    edad = edad.text.toInt(),
                    deporte = Deporte(deporteId.text.toLong(), "")
                )
                apiService.createRegistration(registration).enqueue(object : Callback<ExtracurricularClassesRegistration> {
                    override fun onResponse(call: Call<ExtracurricularClassesRegistration>, response: Response<ExtracurricularClassesRegistration>) {
                        if (response.isSuccessful) {
                            // Manejar éxito
                        }
                    }

                    override fun onFailure(call: Call<ExtracurricularClassesRegistration>, t: Throwable) {
                        // Manejar error
                    }
                })
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Registrar")
        }
    }
}

