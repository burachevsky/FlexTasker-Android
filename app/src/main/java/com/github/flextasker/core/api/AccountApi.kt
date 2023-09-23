package com.github.flextasker.core.api

import com.github.flextasker.core.api.model.NetworkAccountInfo
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface AccountApi {

    @POST("signin")
    suspend fun signIn(
        @Query("username") userName: String,
        @Query("password") password: String,
    ): NetworkAccountInfo

    @POST("signup")
    suspend fun signUp(
        @Query("username") userName: String,
        @Query("password") password: String,
        @Query("confPassword") confPassword: String
    ): NetworkAccountInfo

    @POST("logout")
    suspend fun logout(): Response<Unit>
}