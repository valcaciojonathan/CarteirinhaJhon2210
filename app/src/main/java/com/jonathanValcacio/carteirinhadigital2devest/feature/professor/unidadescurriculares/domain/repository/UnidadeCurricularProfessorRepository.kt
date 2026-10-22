package com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.domain.repository

import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.domain.model.UnidadeCurricularProfessor

interface UnidadeCurricularProfessorRepository {
    suspend fun listar(): Result<List<UnidadeCurricularProfessor>>
}