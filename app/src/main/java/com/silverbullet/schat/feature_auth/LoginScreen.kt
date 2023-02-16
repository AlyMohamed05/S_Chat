package com.silverbullet.schat.feature_auth

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.silverbullet.schat.R
import com.silverbullet.schat.core.ui.theme.LocalSpacing
import com.silverbullet.schat.core.ui.theme.TextGrey
import com.silverbullet.schat.feature_auth.components.AnimatedLock
import com.silverbullet.schat.feature_auth.components.AuthInputField

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onSignupClick: () -> Unit,
    onLoggedIn: () -> Unit
) {

    val state = viewModel.loginScreenState.collectAsState()

    val scrollState = rememberScrollState()

    val focusManager = LocalFocusManager.current
    val softwareKeyboard = LocalSoftwareKeyboardController.current

    val loginButtonEnabled = viewModel.loginButtonEnabled.collectAsState()
    val loginButtonScale = animateFloatAsState(
        targetValue = if (loginButtonEnabled.value)
            1f
        else 0.7f
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
            .padding(horizontal = LocalSpacing.current.mediumSpace),
        verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.largeSpace),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(LocalConfiguration.current.screenHeightDp.dp * 0.4f))
        AuthInputField(
            value = state.value.usernameText,
            onValueChanged = viewModel::setUsernameValue,
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = TextGrey
            ),
            singleLine = true,
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            ),
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                imeAction = ImeAction.Next
            ),
            label = stringResource(id = R.string.username),
            labelStyle = TextStyle(color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )
        AuthInputField(
            value = state.value.passwordText,
            onValueChanged = viewModel::setPasswordValue,
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = TextGrey
            ),
            label = stringResource(id = R.string.password),
            labelStyle = TextStyle(color = Color.Black),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            hideInput = state.value.shouldHidePassword,
            trailingIcon = {
                Icon(
                    imageVector = if (state.value.shouldHidePassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { viewModel.togglePasswordVisibility() }
                )
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    softwareKeyboard?.hide()
                    viewModel.login()
                }
            )
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.extraLargeSpace))
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .clickable { onLoggedIn() }
        ) {
            AnimatedLock(
                locked = state.value.isLocked,
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = loginButtonScale.value
                        scaleY = loginButtonScale.value
                    }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .clickable { onSignupClick() }
                .padding(LocalSpacing.current.mediumSpace)
        ) {
            Text(
                text = stringResource(id = R.string.signup),
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(LocalSpacing.current.largeSpace))
    }
}