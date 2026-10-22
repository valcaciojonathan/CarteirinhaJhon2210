
package com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.data.repository

import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.data.remote.service.UnidadeCurricularProfessorApi
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.domain.model.UnidadeCurricularProfessor
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.domain.repository.UnidadeCurricularProfessorRepository
import retrofit2.HttpException
import java.io.IOException

class ApiUnidadeCurricularProfessorRepositoryImpl(
    private val api: UnidadeCurricularProfessorApi
) : UnidadeCurricularProfessorRepository {

    override suspend fun listar(): Result<List<UnidadeCurricularProfessor>> {
        return runCatching {
            api.listar().map { it.toDomain() }
        }.recoverCatching { error ->
            when (error) {
                is HttpException -> {
                    throw IllegalStateException(
                        "Erro HTTP ${error.code()} ao carregar as unidades curriculares."
                    )
                }

                is IOException -> {
                    throw IllegalStateException(
                        "Não foi possível conectar à API. Confira se ela está iniciada."
                    )
                }

                else -> {
                    throw IllegalStateException(
                        error.message ?: "Erro ao carregar unidades curriculares."
                    )
                }
            }
        }
    }
}
