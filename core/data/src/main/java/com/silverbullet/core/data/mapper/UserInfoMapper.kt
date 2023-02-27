package com.silverbullet.core.data.mapper

import com.silverbullet.core.model.UserInfo
import com.silverbullet.core.network.auth.model.response.SignupResponse

fun SignupResponse.toUserInfo(): UserInfo = UserInfo(
    id = id,
    name = name,
    username = username
)