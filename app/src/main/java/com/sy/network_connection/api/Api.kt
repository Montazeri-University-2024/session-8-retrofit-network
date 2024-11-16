package com.sy.network_connection.api

import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("users")
    suspend fun getUsers(): Response<GetUserResponse>
}