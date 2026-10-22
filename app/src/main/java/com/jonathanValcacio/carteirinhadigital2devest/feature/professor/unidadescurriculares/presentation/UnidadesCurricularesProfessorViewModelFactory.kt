
package com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.domain.repository.UnidadeCurricularProfessorRepository

class UnidadesCurricularesProfessorViewModelFactory(
    private val repository: UnidadeCurricularProfessorRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(
                UnidadesCurricularesProfessorViewModel::class.java
            )
        ) {
            @Suppress("UNCHECKED_CAST")
            return UnidadesCurricularesProfessorViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "ViewModel desconhecido: ${modelClass.name}"
        )
    }
}
