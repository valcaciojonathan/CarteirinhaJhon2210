package com.jonathanValcacio.carteirinhadigital2devest.feature.login.data.remote.service

import com.jonathanValcacio.carteirinhadigital2devest.feature.login.data.remote.dto.LoginRequestDto
import com.jonathanValcacio.carteirinhadigital2devest.feature.login.data.remote.dto.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body body: LoginRequestDto): LoginResponseDto
}
