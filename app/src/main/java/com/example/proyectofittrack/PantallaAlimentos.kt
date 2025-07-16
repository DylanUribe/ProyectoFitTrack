package com.example.proyectofittrack

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BreakfastDining
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaAlimentos() {
    var nuevaComida by rememberSaveable { mutableStateOf("") }
    val comidas = remember {
        mutableStateListOf(
            "Desayuno: Avena y frutas",
            "Almuerzo: Pollo y arroz",
            "Cena: Ensalada con atún"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Comidas de hoy", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        // Input de nueva comida
        TextField(
            value = nuevaComida,
            onValueChange = { nuevaComida = it },
            label = { Text("Agregar comida") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (nuevaComida.isNotBlank()) {
                    comidas.add(nuevaComida)
                    nuevaComida = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Agregar")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Mostrar lista de comidas
        comidas.forEach { comida ->
            val tiempo = when {
                comida.contains("Desayuno", true) -> "Desayuno"
                comida.contains("Almuerzo", true) -> "Almuerzo"
                comida.contains("Cena", true) -> "Cena"
                else -> "Otro"
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = when (tiempo) {
                            "Desayuno" -> Icons.Default.BreakfastDining
                            "Almuerzo" -> Icons.Default.LunchDining
                            "Cena" -> Icons.Default.DinnerDining
                            else -> Icons.Default.Fastfood
                        },
                        contentDescription = tiempo,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = comida, fontSize = 18.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text("Calorías quemadas hoy", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text("450 kcal", fontSize = 18.sp)
    }
}