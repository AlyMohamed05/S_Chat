package com.silverbullet.schat.feature_home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.silverbullet.schat.core.ui.theme.LocalSpacing
import com.silverbullet.schat.feature_home.components.HomeTop
import com.silverbullet.schat.core.ui.components.DefaultInputField
import com.silverbullet.schat.R
import com.silverbullet.schat.core.model.Chat
import com.silverbullet.schat.core.utils.PreviewData
import com.silverbullet.schat.feature_home.components.ChatItem

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onChatClick: (Chat) -> Unit
) {

    val searchText = viewModel.searchText.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HomeTop(
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
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.mediumSpace)
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.mediumSpace))
        LazyColumn {
            items(1000) {
                ChatItem(
                    chat = PreviewData.chat,
                    onClick = { chat -> onChatClick(chat)},
                    isMyMessage = { true },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(LocalSpacing.current.smallSpace))
            }
        }
    }
}