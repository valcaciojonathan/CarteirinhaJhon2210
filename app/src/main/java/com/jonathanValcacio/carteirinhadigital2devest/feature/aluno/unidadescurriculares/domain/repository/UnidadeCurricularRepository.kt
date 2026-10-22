package com.jonathanValcacio.carteirinhadigital2devest.feature.aluno.unidadescurriculares.domain.repository

import com.jonathanValcacio.carteirinhadigital2devest.feature.aluno.unidadescurriculares.domain.model.UnidadeCurricular

interface UnidadeCurricularRepository {
    suspend fun listar():Result<List<UnidadeCurricular>>
}