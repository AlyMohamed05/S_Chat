package com.silverbullet.schat

import androidx.lifecycle.ViewModel
import com.silverbullet.core.data.preferences.Preferences
import com.silverbullet.schat.feature_auth.authNavGraph
import com.silverbullet.schat.navigation.mainGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val preferences: Preferences
) : ViewModel() {

    var isLoading = true
        private set

    val startDestination by lazy {
        val destination = if (preferences.isLoggedIn())
            mainGraph
        else
            authNavGraph
        isLoading = false
        destination
    }
}