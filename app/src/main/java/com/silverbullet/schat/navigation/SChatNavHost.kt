package com.silverbullet.schat.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.silverbullet.schat.feature_auth.authGraph
import com.silverbullet.schat.feature_auth.authNavGraph

@Composable
fun SChatNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = authNavGraph
    ){
        authGraph()
    }
}
