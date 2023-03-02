package com.silverbullet.schat.feature_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverbullet.core.data.channels.ChannelsRepository
import com.silverbullet.core.data.channels.results.RepoUserChannelsResult
import com.silverbullet.core.data.connections.ConnectionsRepository
import com.silverbullet.core.data.connections.results.RepoConnectionResult
import com.silverbullet.core.data.utils.RepoResult
import com.silverbullet.core.model.ChannelInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val connectionsRepository: ConnectionsRepository,
    private val channelsRepository: ChannelsRepository
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _showAddUserDialog = MutableStateFlow(false)
    val showAddUserDialog = _showAddUserDialog.asStateFlow()

    // when adding a new user this holds the state of input
    private val _usernameFieldState = MutableStateFlow("")
    val usernameFieldState = _usernameFieldState.asStateFlow()

    private val _isTryingToConnectWithUser = MutableStateFlow(false)
    val isTryingToConnectWithUser = _isTryingToConnectWithUser.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _channels = MutableStateFlow<List<ChannelInfo>>(emptyList())
    val channels = _channels.asStateFlow()

    private val _events = MutableSharedFlow<UiEvent>()
    val events = _events.asSharedFlow()

    init {
        loadUserChannels()

        viewModelScope.launch {
            channelsRepository
                .channels
                .collectLatest {
                    _channels.value = it

                }
        }
    }

    fun connectToUser() {
        val username = _usernameFieldState.value
        if (username.isBlank())
            return
        viewModelScope.launch {
            connectionsRepository
                .connectToUser(username)
                .collect { repoResult ->
                    when (repoResult) {
                        is RepoResult.HasResult -> {
                            _isTryingToConnectWithUser.value = false
                            when (repoResult.result) {
                                RepoConnectionResult.ConnectedSuccessfully -> {
                                    _events.emit(
                                        UiEvent.ToastMessage(
                                            "Connected"
                                        )
                                    )
                                    _showAddUserDialog.value = false
                                }

                                RepoConnectionResult.UserNotFound -> _events.emit(
                                    UiEvent.ToastMessage(
                                        "User not found"
                                    )
                                )

                                RepoConnectionResult.AlreadyConnected -> {
                                    _events.emit(
                                        UiEvent.ToastMessage(
                                            "Already connected"
                                        )
                                    )
                                    _showAddUserDialog.value = false
                                }

                                RepoConnectionResult.UnexpectedError -> Unit
                            }
                        }

                        is RepoResult.Loading -> {
                            _isTryingToConnectWithUser.value = true
                        }
                    }
                }
        }
    }

    fun setSearchText(value: String) {
        _searchText.value = value
    }

    fun setUsernameValue(value: String) {
        _usernameFieldState.value = value
    }

    /**
     * This will hide the dialog and will clear it's value
     */
    fun setAddUserDialogState(visible: Boolean) {
        // it should also clear the value
        _usernameFieldState.value = ""
        _showAddUserDialog.value = visible
    }

    private fun loadUserChannels() {
        viewModelScope.launch {
            channelsRepository
                .getUserChannels()
                .collect { repoResult ->
                    when (repoResult) {
                        is RepoResult.HasResult -> {
                            _isLoading.value = false
                            when (repoResult.result) {

                                is RepoUserChannelsResult.Loaded -> Unit

                                RepoUserChannelsResult.Failed -> _events.emit(
                                    UiEvent.ToastMessage(
                                        "Couldn't load"
                                    )
                                )

                            }
                        }

                        is RepoResult.Loading -> _isLoading.value = true
                    }
                }
        }
    }

    sealed interface UiEvent {

        data class ToastMessage(val message: String) : UiEvent
    }
}