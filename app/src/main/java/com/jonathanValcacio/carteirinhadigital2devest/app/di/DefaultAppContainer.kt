package com.jonathanValcacio.carteirinhadigital2devest.app.di

import com.jonathanValcacio.carteirinhadigital2devest.core.auth.SessionTokenStore
import com.jonathanValcacio.carteirinhadigital2devest.core.network.NetworkClient
import com.jonathanValcacio.carteirinhadigital2devest.feature.aluno.unidadescurriculares.data.remote.service.UnidadeCurricularApi
import com.jonathanValcacio.carteirinhadigital2devest.feature.aluno.unidadescurriculares.data.repository.ApiUnidadeCurricularRepositoryImpl
import com.jonathanValcacio.carteirinhadigital2devest.feature.aluno.unidadescurriculares.domain.repository.UnidadeCurricularRepository
import com.jonathanValcacio.carteirinhadigital2devest.feature.login.data.remote.service.AuthApi
import com.jonathanValcacio.carteirinhadigital2devest.feature.login.data.repository.ApiLoginRepositoryImpl
import com.jonathanValcacio.carteirinhadigital2devest.feature.login.data.repository.FakeAuthRepository
import com.jonathanValcacio.carteirinhadigital2devest.feature.login.domain.repository.LoginRepository
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.turmas.data.remote.service.TurmaProfessorApi
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.turmas.data.repository.ApiTurmaProfessorRepositoryImpl
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.turmas.domain.repository.TurmaProfessorRepository
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.data.remote.service.UnidadeCurricularProfessorApi
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.data.repository.ApiUnidadeCurricularProfessorRepositoryImpl
import com.jonathanValcacio.carteirinhadigital2devest.feature.professor.unidadescurriculares.domain.repository.UnidadeCurricularProfessorRepository

class DefaultAppContainer : AppContainer {
    override val sessionTokenStore: SessionTokenStore = SessionTokenStore()
    private val networkClient = NetworkClient(
        baseUrl = BASE_URL,
        sessionTokenStore = sessionTokenStore
    )

    private val authApi: AuthApi by lazy {
        networkClient.createPublic(AuthApi::class.java)
    }

    private val unidadeCurricularApi: UnidadeCurricularApi by lazy {
        networkClient.createAuthenticated(UnidadeCurricularApi::class.java)
    }

    private val turmaProfessorApi: TurmaProfessorApi by lazy {
        networkClient.createAuthenticated(TurmaProfessorApi::class.java)
    }
    private val unidadeCurricularProfessorApi: UnidadeCurricularProfessorApi by lazy {
        networkClient.createAuthenticated(UnidadeCurricularProfessorApi::class.java)
    }
    override val turmaProfessorRepository: TurmaProfessorRepository by lazy {
        ApiTurmaProfessorRepositoryImpl(turmaProfessorApi)
    }
    override val unidadeCurricularProfessorRepository: UnidadeCurricularProfessorRepository by lazy {
        ApiUnidadeCurricularProfessorRepositoryImpl(unidadeCurricularProfessorApi)
    }
    override val loginRepository: LoginRepository by lazy {
        if (USE_FAKE_LOGIN_REPOSITORY) {
            FakeAuthRepository()
        } else {
            ApiLoginRepositoryImpl(api = authApi)
        }
    }
    override val unidadeCurricularRepository: UnidadeCurricularRepository by lazy {
        ApiUnidadeCurricularRepositoryImpl(api = unidadeCurricularApi)
    }

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8080/"
        private const val USE_FAKE_LOGIN_REPOSITORY = false
    }
}