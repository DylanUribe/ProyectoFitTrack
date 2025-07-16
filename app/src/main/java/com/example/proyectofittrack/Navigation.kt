package com.example.proyectofittrack

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun FitnessApp() {
    val navController = rememberNavController()

    // Guardamos email y password registrados (estado en memoria, simple demo)
    var registeredEmail by rememberSaveable { mutableStateOf<String?>(null) }
    var registeredPassword by rememberSaveable { mutableStateOf<String?>(null) }
    var isLoggedIn by rememberSaveable { mutableStateOf(false) }

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) "inicio" else "login"
    ) {
        composable("login") {
            PantallaLogin(
                registeredEmail = registeredEmail,
                registeredPassword = registeredPassword,
                onLoginSuccess = {
                    isLoggedIn = true
                    navController.navigate("inicio") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("registro")
                }
            )
        }
        composable("registro") {
            PantallaRegistro(
                onRegisterSuccess = { email, password ->
                    println("Registrado: $email / $password")
                    registeredEmail = email
                    registeredPassword = password
                    navController.popBackStack("login", false)
                }
            )
        }

        // App principal
        composable("inicio") { PantallaInicio() }
        composable("alimentos") { PantallaAlimentos() }
        composable("progreso") { PantallaProgreso() }
        composable("perfil") {
            PantallaPerfil(
                onLogout = {
                    isLoggedIn = false
                    navController.navigate("login") {
                        popUpTo("inicio") { inclusive = true }
                    }
                }
            )
        }
    }

    if (isLoggedIn) {
        BottomNavigationBar(navController)
    }
}