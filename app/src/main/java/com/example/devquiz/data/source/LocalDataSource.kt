package com.example.devquiz.data.source

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.devquiz.data.model.Categoria
import com.example.devquiz.data.model.Pregunta
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject

private val Context.dataStore by preferencesDataStore(
    name = "devquiz_preferences"
)

class LocalDataSource(
    private val context: Context
) {

    private val preguntasKey =
        stringPreferencesKey("preguntas_usuario")

    val preguntasUsuario: Flow<List<Pregunta>> =
        context.dataStore.data.map { preferencias ->

            val json =
                preferencias[preguntasKey] ?: "[]"

            convertirJsonAPreguntas(json)
        }

    suspend fun guardarPregunta(
        pregunta: Pregunta
    ) {
        context.dataStore.edit { preferencias ->

            val jsonActual =
                preferencias[preguntasKey] ?: "[]"

            val preguntas =
                convertirJsonAPreguntas(jsonActual).toMutableList()

            preguntas.add(pregunta)

            preferencias[preguntasKey] =
                convertirPreguntasAJson(preguntas)
        }
    }

    private fun convertirJsonAPreguntas(
        json: String
    ): List<Pregunta> {

        val arreglo = JSONArray(json)
        val preguntas = mutableListOf<Pregunta>()

        for (i in 0 until arreglo.length()) {
            val objeto = arreglo.getJSONObject(i)
            val opcionesJson =
                objeto.getJSONArray("opciones")

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
                    creadaPorUsuario = true
                )
            )
        }

        return preguntas
    }

    private fun convertirPreguntasAJson(
        preguntas: List<Pregunta>
    ): String {

        val arreglo = JSONArray()

        preguntas.forEach { pregunta ->

            val opcionesJson = JSONArray()

            pregunta.opciones.forEach { opcion ->
                opcionesJson.put(opcion)
            }

            val objeto = JSONObject().apply {
                put("id", pregunta.id)
                put("texto", pregunta.texto)
                put("opciones", opcionesJson)
                put(
                    "respuestaCorrecta",
                    pregunta.respuestaCorrecta
                )
                put(
                    "categoria",
                    pregunta.categoria.name
                )
            }

            arreglo.put(objeto)
        }

        return arreglo.toString()
    }
}