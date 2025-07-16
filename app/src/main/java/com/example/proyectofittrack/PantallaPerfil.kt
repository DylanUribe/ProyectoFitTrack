package com.example.proyectofittrack

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PantallaPerfil(onLogout: () -> Unit) {
    val context = LocalContext.current
    val userPrefs = remember { UserPreferences(context) }

    var nombre by remember { mutableStateOf(TextFieldValue("Juan Pérez")) }
    var edad by remember { mutableStateOf(TextFieldValue("28")) }
    var fotoUri by remember { mutableStateOf<Uri?>(null) }

    val coroutineScope = rememberCoroutineScope()

    // Launcher para abrir la galería
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            fotoUri = uri
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Perfil de Usuario", fontSize = 26.sp, modifier = Modifier.padding(bottom = 16.dp))

        // Imagen de perfil
        Box(modifier = Modifier.padding(8.dp)) {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(fotoUri ?: "https://i.pravatar.cc/150?u=${nombre.text}")
                    .crossfade(true)
                    .build()
            )
            Image(
                painter = painter,
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )
        }

        Button(onClick = { launcher.launch("image/*") }) {
            Text("Cambiar foto")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Nombre editable
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Edad editable
        OutlinedTextField(
            value = edad,
            onValueChange = { edad = it },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    userPrefs.saveUserData(
                        name = nombre.text,
                        age = edad.text,
                        photoUri = fotoUri?.toString()
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar cambios")
        }


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión", color = Color.White)
        }
    }
}
