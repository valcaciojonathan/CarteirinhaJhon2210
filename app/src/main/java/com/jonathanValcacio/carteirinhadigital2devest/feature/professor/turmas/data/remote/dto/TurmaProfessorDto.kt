package com.jonathanValcacio.carteirinhadigital2devest.feature.professor.turmas.data.remote.dto

import kotlinx.serialization.Serializable
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.turmas.domain.model.TurmaProfessor

@Serializable
data class TurmaProfessorDto(
    val id: String,
    val codigo: String,
    val curso: String,
    val semestre: String,
    val quantidadeAlunos: Int,
    val diasSemana: List<String>,
    val horarioInicio: String,
    val horarioFim: String
) {
    fun toDomain() = TurmaProfessor(id, codigo, curso, semestre, quantidadeAlunos, diasSemana, horarioInicio, horarioFim)
}
