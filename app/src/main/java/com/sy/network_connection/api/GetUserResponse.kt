package com.sy.network_connection.api

import com.google.gson.annotations.SerializedName

data class GetUserResponse(val page: Int, val data: List<User>)

data class User(
    val id: Int,
    val email: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val avatar: String
)