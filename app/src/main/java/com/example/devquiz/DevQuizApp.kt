package com.example.devquiz

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.devquiz.data.repository.QuizRepository
import com.example.devquiz.navigation.AppNavigation
import com.example.devquiz.presentation.viewmodel.FormViewModel
import com.example.devquiz.presentation.viewmodel.QuizViewModel

@Composable
fun DevQuizApp() {
    val context = LocalContext.current

    val repository = remember {
        QuizRepository(context.applicationContext)
    }

    val quizViewModel = remember {
        QuizViewModel(repository)
    }

    val formViewModel = remember {
        FormViewModel(repository)
    }

    AppNavigation(
        quizViewModel = quizViewModel,
        formViewModel = formViewModel
    )
}