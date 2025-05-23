package com.alfonso.usersapp.ui.modules.users.viewstate

import com.alfonso.usersapp.domain.conection.NetworkStatus
import com.alfonso.usersapp.ui.modules.users.model.UsersUIModel

data class UsersViewState(
    val response: List<UsersUIModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val fromCache: Boolean = false,
    val networkStatus: NetworkStatus? = null
){
    fun updateUsers(
        response: List<UsersUIModel>
    ) = copy(response = response, error = null, fromCache = false)
}
