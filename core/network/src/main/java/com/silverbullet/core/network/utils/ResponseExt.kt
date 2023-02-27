package com.silverbullet.core.network.utils

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.Response

fun <T> Response<T>.toApiErrorResponse(json: Json): ApiErrorResponse? {
    val body =
        this.errorBody()?.string() ?: return ApiErrorResponse.Default
    return try {
        json.decodeFromString<ApiErrorResponse>(body)
    } catch (e: Exception) {
        null
    }
}