package com.silverbullet.schat.feature_auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

const val authNavGraph = "auth_nav_graph"
const val loginRoute = "login_route"
const val signupRoute = "signup_route"

@Composable
fun AuthNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = loginRoute
    ) {
        loginScreen()
        signupScreen()
    }
}

fun NavGraphBuilder.authGraph(){
    composable(authNavGraph){
        AuthNavHost()
    }
}

fun NavGraphBuilder.loginScreen() {
    composable(loginRoute) {
        LoginScreen()
    }
}

fun NavGraphBuilder.signupScreen() {
    composable(signupRoute) {
        SignupScreen()
    }
}

fun NavController.navigateToSignupScreen() {
    this.navigate(signupRoute)
}