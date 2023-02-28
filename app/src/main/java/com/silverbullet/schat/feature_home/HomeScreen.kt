package com.silverbullet.schat.feature_home

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.silverbullet.schat.core.ui.theme.LocalSpacing
import com.silverbullet.schat.feature_home.components.HomeTop
import com.silverbullet.schat.core.ui.components.DefaultInputField
import com.silverbullet.schat.R
import com.silverbullet.schat.core.model.Chat

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onChatClick: (Chat) -> Unit
) {

    val searchText = viewModel.searchText.collectAsState()

    val usernameState = viewModel.usernameFieldState.collectAsState()
    val showAddUserDialog = viewModel.showAddUserDialog.collectAsState()
    val isTryingToConnectToUser = viewModel.isTryingToConnectWithUser.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel
            .events
            .collect { event ->
                when (event) {
                    is HomeViewModel.UiEvent.ToastMessage -> Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HomeTop(
            onAddClick = {
                viewModel.setAddUserDialogState(visible = true)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = LocalSpacing.current.largeSpace,
                    start = LocalSpacing.current.largeSpace,
                    end = LocalSpacing.current.largeSpace,
                    bottom = LocalSpacing.current.mediumSpace
                )
        )
        DefaultInputField(
            text = searchText.value,
            onValueChange = viewModel::setSearchText,
            hint = stringResource(id = R.string.search_hint),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.mediumSpace)
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.mediumSpace))
        LazyColumn {
            // TODO: Show the channels for the user
        }
    }

    if (showAddUserDialog.value) {
        AlertDialog(
            onDismissRequest = {
                viewModel.setAddUserDialogState(visible = false)
            },
            title = {
                Text(
                    text = "Username"
                )
            },
            text = {
                TextField(
                    value = usernameState.value,
                    onValueChange = viewModel::setUsernameValue,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = false,
                        imeAction = ImeAction.Go
                    ),
                    keyboardActions = KeyboardActions(
                        onGo = {
                            viewModel.connectToUser()
                        }
                    )
                )
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = LocalSpacing.current.smallSpace),
                    horizontalArrangement = Arrangement.End
                ) {
                    if (isTryingToConnectToUser.value) {
                        CircularProgressIndicator()
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = viewModel::connectToUser) {
                        Text(
                            text = "Connect",
                            color = MaterialTheme.colors.onSurface
                        )
                    }
                }
            },
            backgroundColor =
            if (MaterialTheme.colors.isLight)
                Color.LightGray.copy(alpha = 0.7f)
            else
                Color.DarkGray.copy(alpha = 0.7f),
            shape = RoundedCornerShape(42.dp)
        )
    }
}