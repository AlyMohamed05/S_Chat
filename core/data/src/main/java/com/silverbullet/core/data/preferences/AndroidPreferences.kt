package com.silverbullet.core.data.preferences

import android.content.SharedPreferences
import com.silverbullet.core.model.UserInfo
import javax.inject.Inject

class AndroidPreferences @Inject constructor(
    private val sharedPref: SharedPreferences
) : Preferences {

    override fun isLoggedIn(): Boolean {
        return sharedPref
            .getBoolean(Preferences.IS_LOGGED_IN, false)
    }

    override fun setIsLoggedIn(value: Boolean) {
        sharedPref
            .edit()
            .putBoolean(Preferences.IS_LOGGED_IN, value)
            .apply()
    }

    override fun saveUserInfo(userInfo: UserInfo) {
        sharedPref
            .edit()
            .putInt(Preferences.KEY_USER_ID, userInfo.id)
            .putString(Preferences.KEY_USERNAME, userInfo.username)
            .putString(Preferences.KEY_USER_NAME, userInfo.name)
            .apply()
    }

    override fun loadUserInfo(): UserInfo? {
        val userId = sharedPref.getInt(Preferences.KEY_USER_ID, -1)
        if (userId == -1)
            return null
        val username = sharedPref.getString(Preferences.KEY_USERNAME, null) ?: return null
        val name = sharedPref.getString(Preferences.KEY_USER_NAME, null) ?: return null
        return UserInfo(
            id = userId,
            name = name,
            username = username
        )
    }
}