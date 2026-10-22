package com.jonathanValcacio.carteirinhadigital2devest.feature.professor.turmas.data.repository

import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.turmas.data.remote.service.TurmaProfessorApi
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.turmas.domain.model.TurmaProfessor
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.turmas.domain.repository.TurmaProfessorRepository
import retrofit2.HttpException
import java.io.IOException

class ApiTurmaProfessorRepositoryImpl(private val api: TurmaProfessorApi) : TurmaProfessorRepository {
    override suspend fun listar(): Result<List<TurmaProfessor>> = runCatching {
        api.listar().map { it.toDomain() }
    }.recoverCatching { error ->
        throw when (error) {
            is HttpException -> when (error.code()) {
                401, 403 -> IllegalStateException("Sessão inválida ou sem permissão. Entre novamente.")
                else -> IllegalStateException("Erro ao carregar turmas (HTTP ${error.code()}).")
            }
            is IOException -> IllegalStateException("Não foi possível conectar à API. Confira se ela está iniciada.")
            else -> IllegalStateException(error.message ?: "Erro ao carregar turmas.")
        }
    }
}
