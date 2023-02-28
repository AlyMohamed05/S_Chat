package com.silverbullet.core.data

import android.content.SharedPreferences
import com.silverbullet.core.data.preferences.Preferences
import com.silverbullet.core.network.TokenController
import javax.inject.Inject

class TokenControllerImpl @Inject constructor(
    private val sharedPref: SharedPreferences
) : TokenController {

    // TODO: Remove the sharedPreferences from here, it should be delegated to Preferences interface.

    private var hasToken = getAccessToken() != null

    override fun getAccessToken(): String? {
        return sharedPref
            .getString(Preferences.KEY_ACCESS_TOKEN, null)
    }

    override fun getRefreshToken(): String? {
        return sharedPref
            .getString(Preferences.KEY_REFRESH_TOKEN, null)
    }

    override fun saveAccessToken(token: String) {
        sharedPref
            .edit()
            .putString(Preferences.KEY_ACCESS_TOKEN, token)
            .apply()
        hasToken = true
    }

    override fun saveRefreshToken(token: String) {
        sharedPref
            .edit()
            .putString(Preferences.KEY_REFRESH_TOKEN, token)
            .apply()
    }

    override fun hasAccessToken(): Boolean {
        return hasToken
    }
}