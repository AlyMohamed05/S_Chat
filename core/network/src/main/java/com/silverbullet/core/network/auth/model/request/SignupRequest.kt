package com.silverbullet.core.network.auth.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
    val username: String,
    val name: String,
    val password: String
)
