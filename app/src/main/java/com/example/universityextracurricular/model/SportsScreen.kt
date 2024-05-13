package com.example.universityextracurricular.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun SportsScreen(apiService: ApiService) {
    var deportes by remember { mutableStateOf(listOf<Deporte>()) }
    var errorMessage by remember { mutableStateOf("") }

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
    }
}

