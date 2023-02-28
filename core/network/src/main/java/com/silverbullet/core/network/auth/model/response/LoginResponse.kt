package com.silverbullet.core.network.auth.model.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val userId: Int,
    val name: String,
    val accessToken: String,
    val refreshToken: String
)
