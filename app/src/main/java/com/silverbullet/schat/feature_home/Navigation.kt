package com.silverbullet.schat.feature_home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.silverbullet.schat.core.model.Chat

const val homeRoute = "home_route"

fun NavGraphBuilder.homeScreen(onChatClick: (Chat) -> Unit) {
    composable(homeRoute) {
        HomeScreen(onChatClick = onChatClick)
    }
}