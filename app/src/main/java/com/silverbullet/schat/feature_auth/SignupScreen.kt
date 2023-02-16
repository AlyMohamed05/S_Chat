package com.silverbullet.schat.feature_auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
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
import com.silverbullet.schat.core.ui.theme.TextGrey

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignupScreen(
    viewModel: SignupViewModel = hiltViewModel(),
    onSignup: () -> Unit
) {

    val state = viewModel.signupScreenState.collectAsState()

    val scrollState = rememberScrollState()

    val focusManager = LocalFocusManager.current
    val softwareKeyboard = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
            .padding(horizontal = LocalSpacing.current.mediumSpace),
        verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.largeSpace),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(LocalConfiguration.current.screenHeightDp.dp * 0.2f))
        AuthInputField(
            value = state.value.nameText,
            onValueChanged = { viewModel.onEvent(SignupScreenEvent.NameFieldChanged(it)) },
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = TextGrey
            ),
            label = stringResource(id = R.string.name),
            labelStyle = TextStyle(color = Color.Black),
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
            modifier = Modifier.fillMaxWidth()
        )
        AuthInputField(
            value = state.value.usernameText,
            onValueChanged = { viewModel.onEvent(SignupScreenEvent.UsernameFieldChanged(it)) },
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = TextGrey
            ),
            label = stringResource(id = R.string.username),
            labelStyle = TextStyle(color = Color.Black),
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
            modifier = Modifier.fillMaxWidth()
        )
        AuthInputField(
            value = state.value.passwordText,
            onValueChanged = { viewModel.onEvent(SignupScreenEvent.PasswordFieldChanged(it)) },
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = TextGrey
            ),
            label = stringResource(id = R.string.password),
            labelStyle = TextStyle(color = Color.Black),
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { softwareKeyboard?.hide() }),
            modifier = Modifier.fillMaxWidth(),
            hideInput = state.value.shouldHidePassword,
            trailingIcon = {
                Icon(
                    imageVector = if (state.value.shouldHidePassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { viewModel.onEvent(SignupScreenEvent.TogglePasswordVisibility) }
                )
            }
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.extraLargeSpace))
        IconButton(onClick = { viewModel.onEvent(SignupScreenEvent.Signup) }) {
            Icon(painter = painterResource(id = R.drawable.ic_signup), contentDescription = null)
        }
    }
}