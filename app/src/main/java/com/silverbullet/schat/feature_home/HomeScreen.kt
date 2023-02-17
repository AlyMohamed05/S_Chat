package com.silverbullet.schat.feature_home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.silverbullet.schat.core.ui.theme.LocalSpacing
import com.silverbullet.schat.feature_home.components.HomeTop
import com.silverbullet.schat.feature_home.components.SearchField
import com.silverbullet.schat.R
import com.silverbullet.schat.core.utils.PreviewData
import com.silverbullet.schat.feature_home.components.ChatItem

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {

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
        SearchField(
            text = searchText.value,
            oValueChange = viewModel::setSearchText,
            hint = stringResource(id = R.string.search_hint),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.mediumSpace)
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.mediumSpace))
        LazyColumn {
            items(1000) {
                ChatItem(
                    chat = PreviewData.chat,
                    onClick = {},
                    isMyMessage = { true },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(LocalSpacing.current.smallSpace))
            }
        }
    }
}