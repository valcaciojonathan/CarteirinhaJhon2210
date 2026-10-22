package com.jonathanValcacio.carteirinhadigital2devest.feature.professor.turmas.domain.repository

import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.turmas.domain.model.TurmaProfessor

interface TurmaProfessorRepository {
    suspend fun listar(): Result<List<TurmaProfessor>>
}
