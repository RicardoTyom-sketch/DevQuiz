package com.example.devquiz.presentation.screens.addquestion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.devquiz.data.model.Categoria
import com.example.devquiz.presentation.state.FormUiState

@Composable
fun AddQuestionScreen(
    uiState: FormUiState,
    onTextoChange: (String) -> Unit,
    onOpcionChange: (Int, String) -> Unit,
    onRespuestaCorrectaChange: (Int) -> Unit,
    onCategoriaChange: (Categoria) -> Unit,
    onGuardar: () -> Unit,
    onCancelar: () -> Unit
) {
    val valido =
        uiState.texto.isNotBlank() &&
                uiState.opciones.all { it.isNotBlank() } &&
                uiState.respuestaCorrecta != null &&
                uiState.categoria != null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text("Nueva pregunta")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.texto,
            onValueChange = onTextoChange,
            label = {
                Text("Pregunta")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Categoría")

        Row {
            Categoria.values().forEach { categoria ->

                val nombre = when (categoria) {
                    Categoria.ANDROID -> "Android"
                    Categoria.PROGRAMACION -> "Programación"
                    Categoria.COMPOSE -> "Compose"
                }

                FilterChip(
                    selected =
                        uiState.categoria == categoria,
                    onClick = {
                        onCategoriaChange(categoria)
                    },
                    label = {
                        Text(nombre)
                    },
                    modifier = Modifier.padding(
                        end = 6.dp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Opciones y respuesta correcta")

        uiState.opciones.forEachIndexed { indice, opcion ->

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButton(
                    selected =
                        uiState.respuestaCorrecta == indice,
                    onClick = {
                        onRespuestaCorrectaChange(indice)
                    }
                )

                OutlinedTextField(
                    value = opcion,
                    onValueChange = {
                        onOpcionChange(indice, it)
                    },
                    label = {
                        Text("Opción ${indice + 1}")
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = onGuardar,
            enabled = valido,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar pregunta")
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onCancelar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }
    }
}