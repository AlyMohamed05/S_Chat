package com.silverbullet.schat.feature_home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.silverbullet.schat.core.ui.theme.LocalSpacing
import com.silverbullet.schat.feature_home.components.HomeTop

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HomeTop(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalSpacing.current.largeSpace)
        )
    }
}