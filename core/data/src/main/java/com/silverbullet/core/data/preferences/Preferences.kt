package com.silverbullet.core.data.preferences

import com.silverbullet.core.model.UserInfo

interface Preferences {

    fun saveUserInfo(userInfo: UserInfo)

    fun loadUserInfo(): UserInfo?

    companion object{

        const val SHARED_PREF_NAME = "S_Chat_Pref"

        const val KEY_ACCESS_TOKEN = "access_token"

        const val KEY_REFRESH_TOKEN = "refresh_token"

        const val KEY_USER_ID = "user_id"
        const val KEY_USERNAME = "username" // unique username
        const val KEY_USER_NAME = "user_name"

    }
}