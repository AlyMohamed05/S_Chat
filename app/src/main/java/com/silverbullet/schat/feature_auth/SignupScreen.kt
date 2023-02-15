package com.silverbullet.schat.feature_auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun SignupScreen() {
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = "Signup Screen", fontSize = 33.sp, modifier = Modifier.align(Alignment.Center))
    }
}