package com.silverbullet.schat.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.silverbullet.schat.feature_auth.authGraph
import com.silverbullet.schat.feature_chat.chatScreen
import com.silverbullet.schat.feature_chat.navigateToChatScreen
import com.silverbullet.schat.feature_home.homeRoute
import com.silverbullet.schat.feature_home.homeScreen

@Composable
fun SChatNavHost(navController: NavHostController, startDestination: String) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        authGraph(
            onAuthenticated = { navController.navigateToMainGraph() }
        )
        mainGraph()
    }
}

const val mainGraph = "main_graph"

fun NavController.navigateToMainGraph() {
    this.navigate(mainGraph)
}

fun NavGraphBuilder.mainGraph() {
    composable(mainGraph) {
        MainGraph()
    }
}

@Composable
fun MainGraph() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = homeRoute) {
        homeScreen(
            onChatClick = { channelInfo ->
                navController.navigateToChatScreen(
                    channelId = channelInfo.id,
                    username = channelInfo.friend.username
                )
            }
        )
        chatScreen(navigateBack = { navController.popBackStack() })
    }
}