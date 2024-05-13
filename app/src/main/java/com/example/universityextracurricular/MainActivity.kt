package com.example.universityextracurricular

import ExtracurricularClassesRegistration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import com.example.universityextracurricular.model.Deporte
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/") // Cambia la URL base según sea necesario
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniversityExtracurricularApp(apiService)
        }
    }
}

@Composable
fun UniversityExtracurricularApp(apiService: ApiService) {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("University Extracurricular", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { navigateToDeportesScreen(apiService) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Deportes Disponibles")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { navigateToRegistrationScreen(apiService) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrarse")
            }
        }
    }
}

@Composable
fun DeportesScreen(apiService: ApiService) {
    var deportes by remember { mutableStateOf(listOf<Deporte>()) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        apiService.getDeportes().enqueue(object : Callback<List<Deporte>> {
            override fun onResponse(call: Call<List<Deporte>>, response: Response<List<Deporte>>) {
                if (response.isSuccessful) {
                    deportes = response.body() ?: listOf()
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
        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
        } else {
            deportes.forEach { deporte ->
                Text(deporte.nombre, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
fun RegistrationScreen(apiService: ApiService) {
    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var deporteNombre by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Registro de Clases Extracurriculares", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = edad,
            onValueChange = { edad = it },
            label = { Text("Edad") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = deporteNombre,
            onValueChange = { deporteNombre = it },
            label = { Text("Nombre del Deporte") }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                if (nombre.isNotEmpty() && edad.isNotEmpty() && deporteNombre.isNotEmpty()) {
                    registerAlumno(apiService, nombre, edad.toInt(), deporteNombre) {
                        Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar")
        }
    }
}

fun navigateToDeportesScreen(apiService: ApiService) {
    // Lógica de navegación para DeportesScreen
    // Ejemplo: Navegar utilizando un NavigationHostController
}

fun navigateToRegistrationScreen(apiService: ApiService) {
    // Lógica de navegación para RegistrationScreen
    // Ejemplo: Navegar utilizando un NavigationHostController
}

fun registerAlumno(apiService: ApiService, nombre: String, edad: Int, deporteNombre: String, onSuccess: () -> Unit) {
    val registro = ExtracurricularClassesRegistration(
        nombre = nombre,
        edad = edad,
        deporte = Deporte(nombre = deporteNombre)
    )
    apiService.createRegistration(registro).enqueue(object : Callback<ExtracurricularClassesRegistration> {
        override fun onResponse(
            call: Call<ExtracurricularClassesRegistration>,
            response: Response<ExtracurricularClassesRegistration>
        ) {
            if (response.isSuccessful) {
                onSuccess()
            } else {
                // Error response
            }
        }

        override fun onFailure(call: Call<ExtracurricularClassesRegistration>, t: Throwable) {
            // Failure
        }
    })
}
