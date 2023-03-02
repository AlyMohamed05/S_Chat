package com.silverbullet.schat

import androidx.lifecycle.ViewModel
import com.silverbullet.core.data.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val preferences: Preferences
) : ViewModel() {

    var isLoading = true
        private set

    fun isUserLoggedIn(): Boolean {
        return preferences.isLoggedIn()
    }

    fun onFinishLoading() {
        isLoading = false
    }
}