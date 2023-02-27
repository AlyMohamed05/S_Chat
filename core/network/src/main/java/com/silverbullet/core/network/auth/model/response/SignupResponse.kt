package com.silverbullet.core.network.auth.model.response

import kotlinx.serialization.Serializable

@Serializable
data class SignupResponse(
    val id: Int,
    val username: String,
    val name: String
)
