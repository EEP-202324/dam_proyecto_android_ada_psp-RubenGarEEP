package com.example.universityextracurricular.ui

import ExtracurricularClassesRegistration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.universityextracurricular.ApiService
import com.example.universityextracurricular.PageResponse
import com.example.universityextracurricular.model.Deporte
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SportsScreen(apiService: ApiService) {
    var deportes by remember { mutableStateOf(listOf<Deporte>()) }
    var errorMessage by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<ExtracurricularClassesRegistration>>(emptyList()) }

    LaunchedEffect(Unit) {
        apiService.getDeportes().enqueue(object : Callback<List<Deporte>> {
            override fun onResponse(call: Call<List<Deporte>>, response: Response<List<Deporte>>) {
                if (response.isSuccessful) {
                    deportes = response.body() ?: emptyList()
                } else {
                    errorMessage = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<List<Deporte>>, t: Throwable) {
                errorMessage = "Error: ${t.message}"
            }
        })
    }

    fun searchAlumnos() {
        apiService.searchRegistrations(searchQuery, 0, 10, "nombre", "asc")
            .enqueue(object : Callback<PageResponse<ExtracurricularClassesRegistration>> {
                override fun onResponse(
                    call: Call<PageResponse<ExtracurricularClassesRegistration>>,
                    response: Response<PageResponse<ExtracurricularClassesRegistration>>
                ) {
                    if (response.isSuccessful) {
                        searchResults = response.body()?.content ?: emptyList()
                        errorMessage = ""
                        println("DEBUG: Resultados de búsqueda: $searchResults")
                    } else {
                        searchResults = emptyList()
                        errorMessage = "Error: ${response.code()} - ${response.message()}"
                        println("DEBUG: Error en la búsqueda: ${response.code()} - ${response.message()}")
                    }
                }

                override fun onFailure(
                    call: Call<PageResponse<ExtracurricularClassesRegistration>>,
                    t: Throwable
                ) {
                    searchResults = emptyList()
                    errorMessage = "Error: ${t.message}"
                    println("DEBUG: Error de red en la búsqueda: ${t.message}")
                }
            })
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Deportes Disponibles",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
        } else {
            LazyColumn {
                items(deportes) { deporte ->
                    Text(
                        text = "ID: ${deporte.id} - ${deporte.nombre}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar Alumno") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        Button(
            onClick = { searchAlumnos() },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Text("Buscar")
        }

        LazyColumn {
            items(searchResults) { result ->
                if (result.deporte != null) {
                    Text(
                        text = "Nombre: ${result.nombre}, Edad: ${result.edad}, Deporte: ${result.deporte.nombre}, Horario: ${result.horario}",
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    )
                } else {
                    Text(
                        text = "Nombre: ${result.nombre}, Edad: ${result.edad}, Deporte: No registrado, Horario: ${result.horario}",
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    )
                }
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}
