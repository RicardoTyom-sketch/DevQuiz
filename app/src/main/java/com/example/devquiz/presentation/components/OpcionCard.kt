package com.example.devquiz.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun OpcionCard(
    texto: String,
    indice: Int,
    habilitada: Boolean,
    onClick: () -> Unit
) {
    val letra = ('A'.code + indice).toChar()

    OutlinedButton(
        onClick = onClick,
        enabled = habilitada,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "$letra) $texto"
        )
    }
}