package com.example.devquiz.data.source

import android.content.Context
import com.example.devquiz.data.model.Categoria
import com.example.devquiz.data.model.Pregunta
import org.json.JSONArray

class SeedDataSource(
    private val context: Context
) {

    fun cargarPreguntas(): List<Pregunta> {
        val json = context.assets
            .open("questions.json")
            .bufferedReader()
            .use { it.readText() }

        val arreglo = JSONArray(json)
        val preguntas = mutableListOf<Pregunta>()

        for (i in 0 until arreglo.length()) {
            val objeto = arreglo.getJSONObject(i)

            val opcionesJson = objeto.getJSONArray("opciones")
            val opciones = mutableListOf<String>()

            for (j in 0 until opcionesJson.length()) {
                opciones.add(
                    opcionesJson.getString(j)
                )
            }

            preguntas.add(
                Pregunta(
                    id = objeto.getInt("id"),
                    texto = objeto.getString("texto"),
                    opciones = opciones,
                    respuestaCorrecta =
                        objeto.getInt("respuestaCorrecta"),
                    categoria = Categoria.valueOf(
                        objeto.getString("categoria")
                    ),
                    creadaPorUsuario = false
                )
            )
        }

        return preguntas
    }
}