package com.example.universityextracurricular

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.universityextracurricular.model.Deporte
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SportsScreen(apiService: ApiService) {
    var deportes by remember { mutableStateOf(listOf<Deporte>()) }

    LaunchedEffect(Unit) {
        apiService.getDeportes().enqueue(object : Callback<List<Deporte>> {
            override fun onResponse(call: Call<List<Deporte>>, response: Response<List<Deporte>>) {
                if (response.isSuccessful) {
                    deportes = response.body() ?: emptyList()
                }
            }

            override fun onFailure(call: Call<List<Deporte>>, t: Throwable) {
                // Manejar error
            }
        })
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(deportes) { deporte ->
            Text(text = deporte.nombre, style = MaterialTheme.typography.bodyLarge)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}
