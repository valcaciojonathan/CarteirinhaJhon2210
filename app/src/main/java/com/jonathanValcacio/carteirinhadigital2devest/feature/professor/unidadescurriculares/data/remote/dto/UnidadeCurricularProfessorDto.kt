package com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.data.remote.dto

import kotlinx.serialization.Serializable
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.domain.model.UnidadeCurricularProfessor

@Serializable
data class UnidadeCurricularProfessorDto(
    val id: String,
    val nome: String,
    val turmaId: String,
    val turma: String,
    val cargaHoraria: Int,
    val quantidadeAulas: Int,
    val aulasRealizadas: Int,
    val diasSemana: List<String>,
    val horarioInicio: String,
    val horarioFim: String
) {
    fun toDomain() = UnidadeCurricularProfessor(id, nome, turmaId, turma, cargaHoraria, quantidadeAulas, aulasRealizadas, diasSemana, horarioInicio, horarioFim)
}
