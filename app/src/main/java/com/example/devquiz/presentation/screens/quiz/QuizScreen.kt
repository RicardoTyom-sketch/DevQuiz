package com.example.devquiz.presentation.screens.quiz

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
import com.example.devquiz.presentation.components.MarcadorQuiz
import com.example.devquiz.presentation.components.OpcionCard
import com.example.devquiz.presentation.components.PreguntaCard
import com.example.devquiz.presentation.state.QuizUiState

@Composable
fun QuizScreen(
    uiState: QuizUiState,
    onResponder: (Int) -> Unit,
    onSiguiente: () -> Unit,
    onVolver: () -> Unit
) {
    if (uiState.preguntas.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text("No hay preguntas en esta categoría.")

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = onVolver) {
                Text("Volver")
            }
        }

        return
    }

    val pregunta = uiState.preguntas[uiState.indiceActual]
    val respondida = uiState.respuestaSeleccionada != null

    val correcta =
        uiState.respuestaSeleccionada ==
                pregunta.respuestaCorrecta

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        MarcadorQuiz(
            aciertos = uiState.aciertos,
            errores = uiState.errores
        )

        Spacer(modifier = Modifier.height(18.dp))

        PreguntaCard(
            numero = uiState.indiceActual + 1,
            total = uiState.preguntas.size,
            texto = pregunta.texto
        )

        Spacer(modifier = Modifier.height(18.dp))

        pregunta.opciones.forEachIndexed { indice, opcion ->

            OpcionCard(
                texto = opcion,
                indice = indice,
                habilitada = !respondida,
                onClick = {
                    onResponder(indice)
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        if (respondida) {
            val puntos = uiState.aciertos * 100

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = if (correcta) "Correcto" else "Incorrecto",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (correcta) {
                    "Respuesta correcta."
                } else {
                    "La respuesta correcta es: " +
                            pregunta.opciones[pregunta.respuestaCorrecta]
                },
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Puntuación: $puntos",
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onSiguiente,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (
                        uiState.indiceActual ==
                        uiState.preguntas.lastIndex
                    ) {
                        "VER RESULTADO"
                    } else {
                        "SIGUIENTE"
                    }
                )
            }
        }
    }
}