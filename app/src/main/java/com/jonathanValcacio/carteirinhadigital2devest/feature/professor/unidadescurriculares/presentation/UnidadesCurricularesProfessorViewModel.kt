package com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.domain.repository.UnidadeCurricularProfessorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UnidadesCurricularesProfessorViewModel(private val repository: UnidadeCurricularProfessorRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(UnidadesCurricularesProfessorUiState())
    val uiState = _uiState.asStateFlow()
    fun carregar() {
        if (_uiState.value.isLoading) return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            repository.listar().onSuccess { lista ->
                _uiState.update { it.copy(isLoading = false, unidades = lista, errorMessage = null) }
            }.onFailure { erro ->
                _uiState.update { it.copy(isLoading = false, errorMessage = erro.message ?: "Erro ao carregar unidades curriculares.") }
            }
        }
    }
}
