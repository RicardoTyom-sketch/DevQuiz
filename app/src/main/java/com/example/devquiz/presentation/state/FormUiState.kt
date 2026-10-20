package com.example.devquiz.presentation.state

import com.example.devquiz.data.model.Categoria

data class FormUiState(
    val texto: String = "",
    val opciones: List<String> = listOf("", "", "", ""),
    val respuestaCorrecta: Int? = null,
    val categoria: Categoria? = null
)