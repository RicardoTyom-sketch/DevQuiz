package com.example.devquiz.data.repository

import android.content.Context
import com.example.devquiz.data.model.Pregunta
import com.example.devquiz.data.source.LocalDataSource
import com.example.devquiz.data.source.SeedDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuizRepository(
    context: Context
) {

    private val seedDataSource =
        SeedDataSource(context)

    private val localDataSource =
        LocalDataSource(context)

    private val preguntasBase =
        seedDataSource.cargarPreguntas()

    val preguntas: Flow<List<Pregunta>> =
        localDataSource.preguntasUsuario.map { preguntasUsuario ->
            preguntasBase + preguntasUsuario
        }

    suspend fun agregarPregunta(
        pregunta: Pregunta
    ) {
        localDataSource.guardarPregunta(pregunta)
    }
}