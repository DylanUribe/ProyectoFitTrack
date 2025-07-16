package com.example.proyectofittrack

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.LinearProgressIndicator
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaInicio() {
    val progreso = remember { mutableStateOf(75) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(64.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Rutina de hoy", fontSize = 24.sp)
        Text("Macronutrientes cumplidos: ${progreso.value}%", fontSize = 16.sp)

        LinearProgressIndicator(
            progress = progreso.value / 100f,
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            progreso.value = (0..100).random()
        }) {
            Text("Actualizar progreso")
        }
    }
}