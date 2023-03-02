package com.silverbullet.schat.feature_chat

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val chatRoute = "chat_route"

fun NavController.navigateToChatScreen(
    channelId: Int,
    username: String
) {
    this.navigate("$chatRoute/$channelId/$username")
}

fun NavGraphBuilder.chatScreen(navigateBack: () -> Unit) {
    composable(
        "$chatRoute/{channelId}/{username}",
        arguments = listOf(
            navArgument("channelId") {
                this.type = NavType.IntType
            },
            navArgument("username") {
                this.type = NavType.StringType
            }
        )
    ) {
        ChatScreen(onCloseClick = navigateBack)
    }
}