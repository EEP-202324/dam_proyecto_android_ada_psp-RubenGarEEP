package com.example.universityextracurricular

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.universityextracurricular.model.ExtracurricularClassesRegistration
import com.example.universityextracurricular.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                RegistrationScreen()
            }
        }
    }
}

@Composable
fun RegistrationScreen() {
    var registrations by remember { mutableStateOf<List<ExtracurricularClassesRegistration>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        loadRegistrations(
            onSuccess = { data ->
                registrations = data
                isLoading = false
            },
            onError = { error ->
                errorMessage = error
                isLoading = false
            }
        )
    }

    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize().wrapContentSize())
    } else if (errorMessage.isNotEmpty()) {
        Text(text = errorMessage, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(registrations) { registration ->
                RegistrationItem(registration)
            }
        }
    }
}

@Composable
fun RegistrationItem(registration: ExtracurricularClassesRegistration) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = "Nombre: ${registration.nombre}", style = MaterialTheme.typography.body1)
        Text(text = "Edad: ${registration.edad}", style = MaterialTheme.typography.body2)
        Text(text = "Deporte: ${registration.deporte.nombre}", style = MaterialTheme.typography.body2)
        Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

fun loadRegistrations(onSuccess: (List<ExtracurricularClassesRegistration>) -> Unit, onError: (String) -> Unit) {
    RetrofitClient.apiService.getRegistrations(page = 0, size = 10, sort = "id,asc").enqueue(object :
        Callback<List<ExtracurricularClassesRegistration>> {
        override fun onResponse(
            call: Call<List<ExtracurricularClassesRegistration>>,
            response: Response<List<ExtracurricularClassesRegistration>>
        ) {
            if (response.isSuccessful) {
                response.body()?.let { onSuccess(it) }
            } else {
                onError("Error: ${response.message()}")
            }
        }

        override fun onFailure(call: Call<List<ExtracurricularClassesRegistration>>, t: Throwable) {
            onError("Failure: ${t.message}")
        }
    })
}
}