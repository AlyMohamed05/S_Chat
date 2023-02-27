package com.silverbullet.core.network.auth.model.response

import kotlinx.serialization.Serializable

@Serializable
data class TokenData(
    val accessToken: String,
    val refreshToken: String
)
