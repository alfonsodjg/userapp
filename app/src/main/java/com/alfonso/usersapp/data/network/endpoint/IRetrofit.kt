package com.alfonso.usersapp.data.network.endpoint

import com.alfonso.usersapp.data.modules.users.model.UsersDataModel
import retrofit2.Response
import retrofit2.http.GET

interface IRetrofit {
    @GET("todos")
    suspend fun getAllUsers(): Response<List<UsersDataModel>>
}