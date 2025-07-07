package com.example.proyectofittrack

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.proyectofittrack.ui.theme.ProyectoFitTrackTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitnessApp()
        }
    }
}

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


        // Resto de pantallas ...
        // App principal
        composable("inicio") { PantallaInicio() }
        composable("ejercicios") { PantallaEjercicios() }
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
//barra nav con iconos
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Inicio", "inicio", Icons.Default.Home),
        BottomNavItem("Ejercicios", "ejercicios", Icons.Default.FitnessCenter),
        BottomNavItem("Alimentos", "alimentos", Icons.Default.Restaurant),
        BottomNavItem("Progreso", "progreso", Icons.Default.ShowChart),
        BottomNavItem("Perfil", "perfil", Icons.Default.Person)
    )

    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

data class BottomNavItem(val title: String, val route: String, val icon: ImageVector)

@Composable
fun PantallaLogin(
    registeredEmail: String?,
    registeredPassword: String?,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Iniciar Sesión", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo") })
        OutlinedTextField(
            value = password, onValueChange = { password = it },
            label = { Text("Contraseña") }, visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            println("Intento login con: $email / $password, guardado: $registeredEmail / $registeredPassword")
            if (registeredEmail != null && registeredPassword != null &&
                email == registeredEmail && password == registeredPassword) {
                onLoginSuccess()
            } else {
                Toast.makeText(context, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Ingresar")
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = onNavigateToRegister) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}



@Composable
fun PantallaInicio() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(64.dp)
    ) {
        Text("Rutina de hoy", fontSize = 24.sp)
        Text("Macronutrientes cumplidos: 75%", fontSize = 16.sp)
        Text("Progreso semanal", fontSize = 16.sp)
    }
}

@Composable
fun PantallaRegistro(
    onRegisterSuccess: (String, String) -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirm by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registro", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo") })
        OutlinedTextField(
            value = password, onValueChange = { password = it },
            label = { Text("Contraseña") }, visualTransformation = PasswordVisualTransformation()
        )
        OutlinedTextField(
            value = confirm, onValueChange = { confirm = it },
            label = { Text("Confirmar contraseña") }, visualTransformation = PasswordVisualTransformation()
        )

        val context = LocalContext.current

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (password == confirm && email.isNotBlank()) {
                Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                onRegisterSuccess(email, password)
            } else {
                Toast.makeText(context, "Error en los datos", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Registrarse")
        }

    }
}

@Composable
fun PantallaEjercicios() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Rutinas Disponibles", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        val rutinas = listOf("Pecho y espalda", "Piernas", "Cardio", "Abdomen")

        rutinas.forEach { rutina ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = rutina,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun PantallaAlimentos() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Comidas de hoy", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        val comidas = listOf("Desayuno: Avena y frutas", "Almuerzo: Pollo y arroz", "Cena: Ensalada con atún")

        comidas.forEach { comida ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = comida,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Calorías quemadas hoy: 450 kcal", fontSize = 18.sp)
    }
}

@Composable
fun PantallaProgreso() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Progreso Semanal", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        val dias = listOf(
            "Lunes: ✅",
            "Martes: ✅",
            "Miércoles: ❌",
            "Jueves: ✅",
            "Viernes: ✅",
            "Sábado: ❌",
            "Domingo: 🔄"
        )

        dias.forEach { dia ->
            Text(
                text = dia,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
fun PantallaPerfil(onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Perfil de Usuario", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Nombre: Juan Pérez", fontSize = 18.sp)
        Text("Edad: 28 años", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onLogout) {
            Text("Cerrar sesión")
        }
    }
}
