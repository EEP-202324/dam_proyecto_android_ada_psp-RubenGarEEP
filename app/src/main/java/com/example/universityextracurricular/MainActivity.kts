package com.university.extracurricular

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://localhost:8082/api/") // Ajusta esta URL si es necesario
        .build()

    private val apiService = retrofit.create(APIService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                RegistrationScreen(apiService)
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}

@Composable
fun RegistrationScreen(apiService: APIService) {
    var registrations by remember { mutableStateOf(emptyList<ExtracurricularClassesRegistration>()) }
    var errorMessage by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = apiService.getRegistrations(0, 10, "id,asc", null, null).execute()
                if (response.isSuccessful) {
                    registrations = response.body() ?: emptyList()
                } else {
                    errorMessage = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage = "Failure: ${e.message}"
            }
        }
    }

    if (errorMessage.isNotEmpty()) {
        Text(
            text = errorMessage,
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colors.error
        )
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(registrations.size) { index ->
            RegistrationItem(registrations[index])
        }
    }
}

@Composable
fun RegistrationItem(registration: ExtracurricularClassesRegistration) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "Nombre: ${registration.nombre}", style = MaterialTheme.typography.h6)
        Text(text = "Edad: ${registration.edad}", style = MaterialTheme.typography.body1)
        Text(text = "Deporte: ${registration.deporte.nombre}", style = MaterialTheme.typography.body2)
        Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}
