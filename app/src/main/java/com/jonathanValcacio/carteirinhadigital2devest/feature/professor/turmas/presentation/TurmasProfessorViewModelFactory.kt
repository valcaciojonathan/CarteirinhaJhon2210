package com.jonathanValcacio.carteirinhadigital2devest.feature.professor.turmas.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.turmas.domain.repository.TurmaProfessorRepository

class TurmasProfessorViewModelFactory(private val repository: TurmaProfessorRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TurmasProfessorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TurmasProfessorViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel desconhecido: ${modelClass.name}")
    }
}
