package com.silverbullet.schat.feature_chat

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val chatRoute= "chat_route"

fun NavController.navigateToChatScreen(){
    this.navigate(chatRoute)
}

fun NavGraphBuilder.chatScreen(navigateBack: () -> Unit){
    composable(chatRoute){
        ChatScreen(onCloseClick = navigateBack)
    }
}