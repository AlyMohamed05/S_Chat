package com.silverbullet.core.data.mapper

import com.silverbullet.core.model.UserInfo
import com.silverbullet.core.network.auth.model.response.SignupResponse
import com.silverbullet.core.network.model.response.NetworkUserInfo

fun SignupResponse.toUserInfo(): UserInfo = UserInfo(
    id = id,
    name = name,
    username = username
)

fun NetworkUserInfo.toUserInfo(): UserInfo =
    UserInfo(
        id = id,
        username = username,
        name = name
    )