package com.example.devquiz.presentation.state

import com.example.devquiz.data.model.Categoria
import com.example.devquiz.data.model.Pregunta

data class QuizUiState(
    val categoriaSeleccionada: Categoria? = null,
    val preguntas: List<Pregunta> = emptyList(),
    val indiceActual: Int = 0,
    val respuestaSeleccionada: Int? = null,
    val aciertos: Int = 0,
    val errores: Int = 0,
    val terminado: Boolean = false
)