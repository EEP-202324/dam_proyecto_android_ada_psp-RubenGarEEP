package com.example.universityextracurricular.ui

import ExtracurricularClassesRegistration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.universityextracurricular.ApiService
import com.example.universityextracurricular.model.Deporte
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(apiService: ApiService) {
    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var deporteNombre by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("Selecciona un horario") }
    var expandedHorario by remember { mutableStateOf(false) }
    var successMessage by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val horarios = listOf("Lunes, Miércoles", "Martes, Jueves", "Toda la semana")

    fun registerAlumno() {
        if (nombre.isNotEmpty() && edad.isNotEmpty() && deporteNombre.isNotEmpty() && horario != "Selecciona un horario") {
            val registro = ExtracurricularClassesRegistration(
                nombre = nombre,
                edad = edad.toInt(),
                horario = horario,
                deporte = Deporte(id = null, nombre = deporteNombre)
            )

            println("DEBUG: Enviando registro: $registro")

            apiService.createRegistration(registro)
                .enqueue(object : Callback<ExtracurricularClassesRegistration> {
                    override fun onResponse(
                        call: Call<ExtracurricularClassesRegistration>,
                        response: Response<ExtracurricularClassesRegistration>
                    ) {
                        if (response.isSuccessful) {
                            successMessage = "Registro exitoso"
                            errorMessage = ""
                            println("DEBUG: Registro exitoso")
                        } else {
                            successMessage = ""
                            errorMessage = "Error: ${response.code()} - ${response.message()}"
                            println("DEBUG: Error al registrar: ${response.code()} - ${response.message()}")
                        }
                    }

                    override fun onFailure(
                        call: Call<ExtracurricularClassesRegistration>,
                        t: Throwable
                    ) {
                        successMessage = ""
                        errorMessage = "Error: ${t.message}"
                        println("DEBUG: Error de red: ${t.message}")
                    }
                })
        } else {
            errorMessage = "Por favor, completa todos los campos"
            println("DEBUG: Campos incompletos")
        }
    }

    fun deleteAlumno() {
        if (nombre.isNotEmpty() && deporteNombre.isNotEmpty()) {
            apiService.deleteRegistrationByNameAndDeporte(nombre, deporteNombre).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        successMessage = "Registro borrado"
                        errorMessage = ""
                    } else {
                        successMessage = ""
                        errorMessage = "Usuario no registrado"
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
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

        Box(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            OutlinedTextField(
                value = horario,
                onValueChange = {},
                label = { Text("Horario") },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandedHorario = true },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = if (expandedHorario) android.R.drawable.arrow_up_float else android.R.drawable.arrow_down_float),
                        contentDescription = null,
                        modifier = Modifier.clickable { expandedHorario = !expandedHorario }
                    )
                }
            )
            DropdownMenu(
                expanded = expandedHorario,
                onDismissRequest = { expandedHorario = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                horarios.forEach { horarioItem ->
                    DropdownMenuItem(
                        text = { Text(horarioItem) },
                        onClick = {
                            horario = horarioItem
                            expandedHorario = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(250.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                onClick = { registerAlumno() },
                modifier = Modifier.weight(1f).padding(4.dp)
            ) {
                Text("Registrarse")
            }
            Button(
                onClick = { deleteAlumno() },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
                modifier = Modifier.weight(1f).padding(4.dp)
            ) {
                Text("Borrar")
            }
        }

        if (successMessage.isNotEmpty()) {
            Text(
                successMessage,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (errorMessage.isNotEmpty()) {
            Text(
                errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}