package com.example.devquiz.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.devquiz.presentation.screens.addquestion.AddQuestionScreen
import com.example.devquiz.presentation.screens.home.HomeScreen
import com.example.devquiz.presentation.screens.quiz.QuizScreen
import com.example.devquiz.presentation.screens.result.ResultScreen
import com.example.devquiz.presentation.viewmodel.FormViewModel
import com.example.devquiz.presentation.viewmodel.QuizViewModel

private enum class Pantalla {
    INICIO,
    QUIZ,
    RESULTADO,
    AGREGAR
}

@Composable
fun AppNavigation(
    quizViewModel: QuizViewModel,
    formViewModel: FormViewModel
) {
    var pantalla by remember {
        mutableStateOf(Pantalla.INICIO)
    }

    val quizUiState by quizViewModel.uiState.collectAsState()
    val formUiState by formViewModel.uiState.collectAsState()

    LaunchedEffect(quizUiState.terminado) {
        if (quizUiState.terminado) {
            pantalla = Pantalla.RESULTADO
        }
    }

    when (pantalla) {

        Pantalla.INICIO -> {
            HomeScreen(
                onCategoriaSeleccionada = { categoria ->
                    quizViewModel.iniciarQuiz(categoria)
                    pantalla = Pantalla.QUIZ
                },
                onAgregarPregunta = {
                    pantalla = Pantalla.AGREGAR
                }
            )
        }

        Pantalla.QUIZ -> {
            QuizScreen(
                uiState = quizUiState,
                onResponder = quizViewModel::responder,
                onSiguiente = quizViewModel::siguientePregunta,
                onVolver = {
                    pantalla = Pantalla.INICIO
                }
            )
        }

        Pantalla.RESULTADO -> {
            ResultScreen(
                uiState = quizUiState,
                onReintentar = {
                    quizViewModel.reiniciarQuiz()
                    pantalla = Pantalla.QUIZ
                },
                onInicio = {
                    pantalla = Pantalla.INICIO
                }
            )
        }

        Pantalla.AGREGAR -> {
            AddQuestionScreen(
                uiState = formUiState,
                onTextoChange = formViewModel::cambiarTexto,
                onOpcionChange = formViewModel::cambiarOpcion,
                onRespuestaCorrectaChange =
                    formViewModel::cambiarRespuestaCorrecta,
                onCategoriaChange =
                    formViewModel::cambiarCategoria,
                onGuardar = {
                    if (formViewModel.guardarPregunta()) {
                        pantalla = Pantalla.INICIO
                    }
                },
                onCancelar = {
                    formViewModel.limpiar()
                    pantalla = Pantalla.INICIO
                }
            )
        }
    }
}