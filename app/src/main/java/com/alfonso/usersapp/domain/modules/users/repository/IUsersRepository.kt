package com.alfonso.usersapp.domain.modules.users.repository

import com.alfonso.usersapp.domain.core.handler.ServiceDomainHandler
import com.alfonso.usersapp.domain.modules.users.model.UsersDomainModel

interface IUsersRepository {
    suspend fun getAllUsers(): ServiceDomainHandler<List<UsersDomainModel>>
    suspend fun getAllLocalUsers(): List<UsersDomainModel>
    suspend fun deleteAllUsers()
    suspend fun deleteUser(user: UsersDomainModel)
    suspend fun updateUser(user: UsersDomainModel)
}