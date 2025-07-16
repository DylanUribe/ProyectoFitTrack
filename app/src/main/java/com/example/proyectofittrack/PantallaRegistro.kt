package com.example.proyectofittrack

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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