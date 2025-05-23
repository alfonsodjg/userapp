package com.alfonso.usersapp.ui.modules.users.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfonso.usersapp.domain.conection.ConnectivityObserver
import com.alfonso.usersapp.domain.conection.NetworkStatus
import com.alfonso.usersapp.domain.core.handler.ServiceDomainHandler
import com.alfonso.usersapp.domain.modules.users.usecase.DeleteUserUseCase
import com.alfonso.usersapp.domain.modules.users.usecase.DeleteUsersUseCase
import com.alfonso.usersapp.domain.modules.users.usecase.GetLocalUsersUseCase
import com.alfonso.usersapp.domain.modules.users.usecase.GetUsersUseCase
import com.alfonso.usersapp.domain.modules.users.usecase.UpdateUserUseCase
import com.alfonso.usersapp.ui.modules.users.mapper.toDomain
import com.alfonso.usersapp.ui.modules.users.viewstate.UsersViewState
import com.alfonso.usersapp.ui.modules.users.mapper.toUI
import com.alfonso.usersapp.ui.modules.users.model.UsersUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val getLocalUsersUseCase: GetLocalUsersUseCase,
    private val deleteUsersUseCase: DeleteUsersUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val connectivityObserver: ConnectivityObserver
): ViewModel() {

    private val _viewState = MutableLiveData(UsersViewState())
    val viewState: LiveData<UsersViewState> = _viewState


    init {
        connectivityObserver.observe().onEach { status ->
            _viewState.value = _viewState.value?.copy(networkStatus = status)
            when (status) {
                NetworkStatus.Available -> onGetUsers(true)
                NetworkStatus.Lost, NetworkStatus.Unavailable -> onGetUsers(false)
                else -> {}
            }
        }.launchIn(viewModelScope)

    }


    fun onGetUsers(isNetworkAvailable: Boolean) {
        viewModelScope.launch {
            _viewState.value = _viewState.value?.copy(isLoading = true)

            val localUsers = getLocalUsersUseCase().map { it.toUI() }
            if (localUsers.isNotEmpty()) {
                _viewState.value = _viewState.value?.copy(response = localUsers, error = null, fromCache = true)
            }

            if (isNetworkAvailable) {
                when (val result = getUsersUseCase()) {
                    is ServiceDomainHandler.Success -> {
                        val newUsers = result.data.map { it.toUI() }
                        if (newUsers != localUsers) {
                            _viewState.value = _viewState.value?.updateUsers(newUsers)
                        }
                    }

                    is ServiceDomainHandler.Error -> {
                        if (localUsers.isEmpty()) {
                            getCachedUsers(result.exception.message ?: "Error desconocido")
                        }
                        _viewState.value = _viewState.value?.copy(isLoading = false)
                    }
                }
            } else {
                if (localUsers.isEmpty()) {
                    getCachedUsers("")
                }
            }

            _viewState.value = _viewState.value?.copy(isLoading = false)
        }
    }

    private suspend fun getCachedUsers(errorMsg: String) {
        val localUsers = getLocalUsersUseCase().map { it.toUI() }

        if (localUsers.isNotEmpty()) {
            _viewState.value = _viewState.value?.copy(response = localUsers, error = null, fromCache = true)
        } else {
            _viewState.value = _viewState.value?.copy(response = emptyList(), error = "No hay registro de usuarios", fromCache = false)
        }
    }

    fun onUpdateUser(user: UsersUIModel){
        viewModelScope.launch {
            updateUserUseCase(user.toDomain())
        }
    }
    fun deleteAllUsers() {
        viewModelScope.launch {
            deleteUsersUseCase
        }
    }
    fun onDeleteUser(user: UsersUIModel){
        viewModelScope.launch {
            deleteUserUseCase(user.toDomain())
        }
    }
}