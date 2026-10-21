package com.example.devquiz.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devquiz.data.model.Categoria
import com.example.devquiz.data.model.Pregunta
import com.example.devquiz.data.repository.QuizRepository
import com.example.devquiz.presentation.state.FormUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FormViewModel(
    private val repository: QuizRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FormUiState())
    val uiState: StateFlow<FormUiState> =
        _uiState.asStateFlow()

    fun cambiarTexto(texto: String) {
        _uiState.value =
            _uiState.value.copy(texto = texto)
    }

    fun cambiarCategoria(categoria: Categoria) {
        _uiState.value =
            _uiState.value.copy(categoria = categoria)
    }

    fun cambiarOpcion(
        indice: Int,
        texto: String
    ) {
        val estado = _uiState.value

        if (indice !in estado.opciones.indices) {
            return
        }

        val nuevasOpciones =
            estado.opciones.toMutableList()

        nuevasOpciones[indice] = texto

        _uiState.value = estado.copy(
            opciones = nuevasOpciones
        )
    }

    fun cambiarRespuestaCorrecta(indice: Int) {
        _uiState.value =
            _uiState.value.copy(
                respuestaCorrecta = indice
            )
    }

    fun guardarPregunta(): Boolean {
        val estado = _uiState.value

        val formularioValido =
            estado.texto.isNotBlank() &&
                    estado.opciones.all { it.isNotBlank() } &&
                    estado.respuestaCorrecta != null &&
                    estado.categoria != null

        if (!formularioValido) {
            return false
        }

        val pregunta = Pregunta(
            id = (System.currentTimeMillis() % Int.MAX_VALUE).toInt(),
            texto = estado.texto.trim(),
            opciones = estado.opciones.map { it.trim() },
            respuestaCorrecta = estado.respuestaCorrecta!!,
            categoria = estado.categoria!!,
            creadaPorUsuario = true
        )

        viewModelScope.launch {
            repository.agregarPregunta(pregunta)
        }

        limpiar()

        return true
    }

    fun limpiar() {
        _uiState.value = FormUiState()
    }
}