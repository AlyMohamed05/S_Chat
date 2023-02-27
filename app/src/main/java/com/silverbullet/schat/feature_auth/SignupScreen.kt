package com.silverbullet.schat.feature_auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.silverbullet.schat.core.ui.theme.LocalSpacing
import com.silverbullet.schat.feature_auth.components.AuthInputField
import com.silverbullet.schat.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignupScreen(
    viewModel: SignupViewModel = hiltViewModel(),
    onSignedUp: () -> Unit
) {

    val usernameFieldState = viewModel.usernameState.collectAsState()
    val nameFieldState = viewModel.nameFieldState.collectAsState()
    val passwordFieldState = viewModel.passwordFieldState.collectAsState()
    val hidePassword = viewModel.hidePassword.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()

    val scrollState = rememberScrollState()

    val focusManager = LocalFocusManager.current
    val softwareKeyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = Unit) {
        viewModel.events.collect { event ->
            when (event) {
                SignupViewModel.UiEvent.OnSignupClass -> {
                    onSignedUp()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = LocalSpacing.current.mediumSpace),
        verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.largeSpace),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(LocalConfiguration.current.screenHeightDp.dp * 0.2f))
        AuthInputField(
            value = nameFieldState.value.value,
            onValueChanged = { viewModel.onEvent(SignupScreenEvent.NameFieldChanged(it)) },
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = MaterialTheme.colors.onSurface
            ),
            label = stringResource(id = R.string.name),
            labelStyle = TextStyle(color = MaterialTheme.colors.onSurface),
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                imeAction = ImeAction.Next
            ),
            hasError = nameFieldState.value.hasError,
            error = nameFieldState.value.error,
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
            modifier = Modifier.fillMaxWidth()
        )
        AuthInputField(
            value = usernameFieldState.value.value,
            onValueChanged = { viewModel.onEvent(SignupScreenEvent.UsernameFieldChanged(it)) },
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = MaterialTheme.colors.onSurface
            ),
            label = stringResource(id = R.string.username),
            labelStyle = TextStyle(color = MaterialTheme.colors.onSurface),
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                imeAction = ImeAction.Next
            ),
            error = usernameFieldState.value.error,
            hasError = usernameFieldState.value.hasError,
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
            modifier = Modifier.fillMaxWidth()
        )
        AuthInputField(
            value = passwordFieldState.value.value,
            onValueChanged = { viewModel.onEvent(SignupScreenEvent.PasswordFieldChanged(it)) },
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = MaterialTheme.colors.onSurface
            ),
            label = stringResource(id = R.string.password),
            labelStyle = TextStyle(color = MaterialTheme.colors.onSurface),
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                imeAction = ImeAction.Done,
            ),
            hasError = passwordFieldState.value.hasError,
            error = passwordFieldState.value.error,
            keyboardActions = KeyboardActions(
                onDone = {
                    softwareKeyboard?.hide()
                    viewModel.onEvent(SignupScreenEvent.Signup)
                }
            ),
            modifier = Modifier.fillMaxWidth(),
            hideInput = hidePassword.value,
            trailingIcon = {
                Icon(
                    imageVector = if (hidePassword.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .clickable { viewModel.onEvent(SignupScreenEvent.TogglePasswordVisibility) }
                )
            }
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.extraLargeSpace))
        IconButton(onClick = { viewModel.onEvent(SignupScreenEvent.Signup) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_signup),
                contentDescription = null
            )
        }
    }
}

