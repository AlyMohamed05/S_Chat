package com.silverbullet.core.network.utils

import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorResponse(
    val error: String,
    val errorCode: Int
){
    companion object{
        val Default = ApiErrorResponse(
            error = "",
            errorCode = ApiErrorCodes.UnexpectedErrorCode,
        )
    }
}
