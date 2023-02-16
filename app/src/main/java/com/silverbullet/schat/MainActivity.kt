package com.silverbullet.schat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.silverbullet.schat.navigation.SChatNavHost
import com.silverbullet.schat.core.ui.theme.SChatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            SChatTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    val navController = rememberNavController()
                    SChatNavHost(navController = navController)
                }
            }
        }
    }
}