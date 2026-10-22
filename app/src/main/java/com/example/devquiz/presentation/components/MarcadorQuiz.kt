package com.example.devquiz.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MarcadorQuiz(
    aciertos: Int,
    errores: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AssistChip(
            onClick = {},
            label = {
                Text("Aciertos: $aciertos")
            }
        )

        AssistChip(
            onClick = {},
            label = {
                Text("Errores: $errores")
            }
        )
    }
}