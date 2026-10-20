package com.example.devquiz.data.model

data class Pregunta(
    val id: Int,
    val texto: String,
    val opciones: List<String>,
    val respuestaCorrecta: Int,
    val categoria: Categoria,
    val creadaPorUsuario: Boolean
)