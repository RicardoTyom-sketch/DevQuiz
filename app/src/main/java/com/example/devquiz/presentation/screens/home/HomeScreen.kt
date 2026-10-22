package com.example.devquiz.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.devquiz.data.model.Categoria
import com.example.devquiz.presentation.components.CategoriaCard

@Composable
fun HomeScreen(
    onCategoriaSeleccionada: (Categoria) -> Unit,
    onAgregarPregunta: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "DevQuiz",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Selecciona una categoría para comenzar.",
            modifier = Modifier.padding(
                top = 6.dp,
                bottom = 24.dp
            )
        )

        CategoriaCard(
            titulo = "Android",
            descripcion = "Conceptos básicos de desarrollo Android.",
            onClick = {
                onCategoriaSeleccionada(Categoria.ANDROID)
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        CategoriaCard(
            titulo = "Programación",
            descripcion = "Preguntas generales de programación.",
            onClick = {
                onCategoriaSeleccionada(Categoria.PROGRAMACION)
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        CategoriaCard(
            titulo = "Jetpack Compose",
            descripcion = "Interfaz, estado y componentes de Compose.",
            onClick = {
                onCategoriaSeleccionada(Categoria.COMPOSE)
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onAgregarPregunta,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar pregunta")
        }
    }
}