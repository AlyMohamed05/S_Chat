package com.silverbullet.schat.feature_auth

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

const val authNavGraph = "auth_nav_graph"
const val loginRoute = "login_route"
const val signupRoute = "signup_route"

@Composable
fun AuthNavHost(
    onLoggedIn: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = loginRoute
    ) {
        loginScreen(
            onSignupClick = { navController.navigateToSignupScreen() },
            onAuthenticated = onLoggedIn
        )
        signupScreen(onSignedUp = navController::popBackStack)
    }
}

fun NavGraphBuilder.authGraph(
    onAuthenticated: () -> Unit
) {
    composable(authNavGraph) {
        AuthNavHost(onLoggedIn = onAuthenticated)
    }
}

fun NavGraphBuilder.loginScreen(
    onSignupClick: () -> Unit,
    onAuthenticated: () -> Unit
) {
    composable(loginRoute) {
        LoginScreen(
            onSignupClick = onSignupClick,
            onLoggedIn = onAuthenticated
        )
    }
}

fun NavGraphBuilder.signupScreen(
    onSignedUp: () -> Unit
) {
    composable(signupRoute) {
        SignupScreen(onSignedUp = onSignedUp)
    }
}

fun NavController.navigateToSignupScreen() {
    this.navigate(signupRoute)
}