package com.example.devquiz.presentation.screens.result

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.devquiz.presentation.state.QuizUiState

@Composable
fun ResultScreen(
    uiState: QuizUiState,
    onReintentar: () -> Unit,
    onInicio: () -> Unit
) {
    val total = uiState.aciertos + uiState.errores
    val puntuacion = uiState.aciertos * 100

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = "Resultados",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "${uiState.aciertos} / $total",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text("Correctas: ${uiState.aciertos}")

        Spacer(modifier = Modifier.height(12.dp))

        Text("Incorrectas: ${uiState.errores}")

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Puntuación: $puntuacion",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onReintentar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("REINTENTAR")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onInicio,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("VOLVER AL INICIO")
        }
    }
}