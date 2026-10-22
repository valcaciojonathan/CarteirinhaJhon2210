package com.jonathanValcacio.carteirinhadigital2devest.feature.login.domain.repository

import com.jonathanValcacio.carteirinhadigital2devest.feature.login.domain.model.UsuarioLogado

interface LoginRepository {
    suspend fun login(login: String, senha: String): Result<UsuarioLogado>
}