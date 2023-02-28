package com.silverbullet.schat.feature_home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.silverbullet.core.model.ChannelInfo

const val homeRoute = "home_route"

fun NavGraphBuilder.homeScreen(onChatClick: (ChannelInfo) -> Unit) {
    composable(homeRoute) {
        HomeScreen(onChannelClick = onChatClick)
    }
}