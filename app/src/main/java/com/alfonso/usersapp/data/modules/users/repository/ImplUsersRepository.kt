package com.alfonso.usersapp.data.modules.users.repository

import com.alfonso.usersapp.data.core.handler.ServiceErrorData
import com.alfonso.usersapp.data.local.dao.UserDao
import com.alfonso.usersapp.data.local.mapper.toDomain
import com.alfonso.usersapp.data.local.mapper.toEntity
import com.alfonso.usersapp.data.modules.users.mapper.toDomain
import com.alfonso.usersapp.data.network.endpoint.IRetrofit
import com.alfonso.usersapp.data.utils.castErrorToServiceDataError
import com.alfonso.usersapp.data.utils.toServiceErrorDomain
import com.alfonso.usersapp.domain.core.handler.ServiceDomainHandler
import com.alfonso.usersapp.domain.modules.users.model.UsersDomainModel
import com.alfonso.usersapp.domain.modules.users.repository.IUsersRepository
import javax.inject.Inject

class ImplUsersRepository @Inject constructor(
    private val retrofit: IRetrofit,
    private val userDao: UserDao
) : IUsersRepository {
    override suspend fun getAllUsers(): ServiceDomainHandler<List<UsersDomainModel>> {

        return try {
            val response = retrofit.getAllUsers()

            if (response.isSuccessful) {
                val body = response.body() ?: emptyList()
                //Save local data
                userDao.insertAll(body.map { it.toDomain().toEntity() })
                ServiceDomainHandler.Success(body.map { it.toDomain() })
            } else {
                val code = response.code()
                val message = response.message()
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                ServiceDomainHandler.Error(
                    ServiceErrorData.ClientError(code, message, errorBody).toServiceErrorDomain()
                )
            }
        } catch (e: Exception) {
            ServiceDomainHandler.Error(
                e.castErrorToServiceDataError().toServiceErrorDomain()
            )
        }

    }

    override suspend fun getAllLocalUsers(): List<UsersDomainModel> {
        return userDao.getAllUsers().map { it.toDomain() }
    }

    override suspend fun deleteAllUsers() {
        userDao.deleteAllUsers()
    }

    override suspend fun deleteUser(user: UsersDomainModel) {
        userDao.deleteUser(user.toEntity())
    }

    override suspend fun updateUser(user: UsersDomainModel) {
        userDao.updateUser(user.toEntity())
    }
}