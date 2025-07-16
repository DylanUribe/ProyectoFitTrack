package com.example.proyectofittrack

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class EstadoProgreso {
    COMPLETADO, FALTA, PENDIENTE
}

@Composable
fun PantallaProgreso() {
    val nombresDias = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")

    // Usamos un estado mutable para que se pueda modificar en tiempo real
    val diasEstado = remember {
        mutableStateListOf(
            *nombresDias.map { DiaProgreso(it, EstadoProgreso.PENDIENTE) }.toTypedArray()
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Progreso Semanal",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            itemsIndexed(diasEstado) { index, dia ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    elevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = when (dia.estado) {
                                    EstadoProgreso.COMPLETADO -> Icons.Default.Check
                                    EstadoProgreso.FALTA -> Icons.Default.Close
                                    EstadoProgreso.PENDIENTE -> Icons.Default.HourglassEmpty
                                },
                                contentDescription = null,
                                tint = when (dia.estado) {
                                    EstadoProgreso.COMPLETADO -> Color(0xFF4CAF50)
                                    EstadoProgreso.FALTA -> Color(0xFFF44336)
                                    EstadoProgreso.PENDIENTE -> Color(0xFFFFC107)
                                },
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = dia.nombre,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Button(
                            onClick = {
                                // Cambiar el estado al siguiente valor cíclico
                                diasEstado[index] = dia.copy(
                                    estado = when (dia.estado) {
                                        EstadoProgreso.PENDIENTE -> EstadoProgreso.COMPLETADO
                                        EstadoProgreso.COMPLETADO -> EstadoProgreso.FALTA
                                        EstadoProgreso.FALTA -> EstadoProgreso.PENDIENTE
                                    }
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.LightGray
                            )
                        ) {
                            Text("Cambiar")
                        }
                    }
                }
            }
        }
    }
}

data class DiaProgreso(val nombre: String, val estado: EstadoProgreso)
