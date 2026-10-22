package com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.data.remote.service

import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.data.remote.dto.UnidadeCurricularProfessorDto
import retrofit2.http.GET

interface UnidadeCurricularProfessorApi {
    @GET("professores/me/unidades-curriculares")
    suspend fun listar(): List<UnidadeCurricularProfessorDto>
}
