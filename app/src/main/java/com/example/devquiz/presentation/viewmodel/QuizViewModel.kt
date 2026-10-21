package com.example.devquiz.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devquiz.data.model.Categoria
import com.example.devquiz.data.model.Pregunta
import com.example.devquiz.data.repository.QuizRepository
import com.example.devquiz.presentation.state.QuizUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class QuizViewModel(
    private val repository: QuizRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    private var preguntasDisponibles: List<Pregunta> = emptyList()

    init {
        viewModelScope.launch {
            repository.preguntas.collectLatest { preguntas ->
                preguntasDisponibles = preguntas
            }
        }
    }

    fun iniciarQuiz(categoria: Categoria) {
        val preguntasCategoria =
            preguntasDisponibles.filter {
                it.categoria == categoria
            }

        _uiState.value = QuizUiState(
            categoriaSeleccionada = categoria,
            preguntas = preguntasCategoria
        )
    }

    fun responder(indiceOpcion: Int) {
        val estado = _uiState.value

        if (
            estado.preguntas.isEmpty() ||
            estado.terminado ||
            estado.respuestaSeleccionada != null
        ) {
            return
        }

        val preguntaActual =
            estado.preguntas[estado.indiceActual]

        val esCorrecta =
            indiceOpcion == preguntaActual.respuestaCorrecta

        _uiState.value = estado.copy(
            respuestaSeleccionada = indiceOpcion,
            aciertos = estado.aciertos + if (esCorrecta) 1 else 0,
            errores = estado.errores + if (esCorrecta) 0 else 1
        )
    }

    fun siguientePregunta() {
        val estado = _uiState.value

        if (estado.respuestaSeleccionada == null) {
            return
        }

        val siguienteIndice =
            estado.indiceActual + 1

        if (siguienteIndice >= estado.preguntas.size) {
            _uiState.value = estado.copy(
                terminado = true
            )
        } else {
            _uiState.value = estado.copy(
                indiceActual = siguienteIndice,
                respuestaSeleccionada = null
            )
        }
    }

    fun reiniciarQuiz() {
        val categoria =
            _uiState.value.categoriaSeleccionada ?: return

        iniciarQuiz(categoria)
    }
}