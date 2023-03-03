package com.silverbullet.schat

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.silverbullet.schat.navigation.SChatNavHost
import com.silverbullet.schat.core.ui.theme.SChatTheme
import com.silverbullet.schat.feature_auth.authNavGraph
import com.silverbullet.schat.navigation.mainGraph
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition { viewModel.isLoading }
        }
        setContent {
            SChatTheme {
                Surface(color = MaterialTheme.colors.surface) {

                    val navController = rememberNavController()
                    var startDestination by remember {
                        mutableStateOf<String?>(null)
                    }

                    startDestination?.let { destination ->
                        SChatNavHost(navController = navController, destination)
                    }

                    LaunchedEffect(key1 = Unit) {
                        startDestination = if (viewModel.isUserLoggedIn())
                            mainGraph
                        else
                            authNavGraph
                        delay(100) // some delay to allow content to appear when splash screen disappear
                        viewModel.onFinishLoading()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val eventService = Intent(applicationContext, EventsService::class.java)
        startService(eventService)
    }

    override fun onStop() {
        super.onStop()
        val eventService = Intent(applicationContext, EventsService::class.java)
        stopService(eventService)
    }
}