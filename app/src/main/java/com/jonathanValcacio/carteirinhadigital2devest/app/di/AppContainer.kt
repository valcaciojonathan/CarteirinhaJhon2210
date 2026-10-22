package com.jonathanValcacio.carteirinhadigital2devest.app.di

import com.jonathanValcacio.carteirinhadigital2devest.core.auth.SessionTokenStore
import com.jonathanValcacio.carteirinhadigital2devest.feature.aluno.unidadescurriculares.domain.repository.UnidadeCurricularRepository
import com.jonathanValcacio.carteirinhadigital2devest.feature.login.domain.repository.LoginRepository
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.turmas.domain.repository.TurmaProfessorRepository
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.domain.repository.UnidadeCurricularProfessorRepository

interface AppContainer {
    val sessionTokenStore: SessionTokenStore
    val loginRepository: LoginRepository
    val unidadeCurricularRepository: UnidadeCurricularRepository
    val turmaProfessorRepository: TurmaProfessorRepository
    val unidadeCurricularProfessorRepository: UnidadeCurricularProfessorRepository
}
