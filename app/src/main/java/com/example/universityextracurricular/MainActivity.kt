package com.example.universityextracurricular


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.universityextracurricular.network.RetrofitClient
import com.example.universityextracurricular.ui.*
import com.example.universityextracurricular.ui.theme.UniversityextracurricularTheme

class MainActivity : ComponentActivity() {
    private val apiService: ApiService by lazy { RetrofitClient.apiService }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniversityextracurricularTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") { MainScreen(navController) }
                    composable("sports") { SportsScreen(apiService) }
                    composable("register") { RegistrationScreen(apiService) }
                }
            }
        }
    }
}

