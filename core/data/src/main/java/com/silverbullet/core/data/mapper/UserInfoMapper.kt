package com.silverbullet.core.data.mapper

import com.example.core.database.entity.UserInfoEntity
import com.silverbullet.core.model.UserInfo
import com.silverbullet.core.network.auth.model.response.SignupResponse

fun SignupResponse.toUserInfo(): UserInfo = UserInfo(
    id = id,
    name = name,
    username = username
)

fun UserInfoEntity.toExternalModel(): UserInfo =
    UserInfo(
        id = id,
        username = username,
        name = name
    )