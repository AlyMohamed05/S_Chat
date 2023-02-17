package com.silverbullet.schat.core.model

data class User(
    val name: String,
    val photoUrl: String?,
    val online: Boolean,
    val id: Long
)
