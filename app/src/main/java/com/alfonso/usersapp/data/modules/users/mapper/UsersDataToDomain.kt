package com.alfonso.usersapp.data.modules.users.mapper

import com.alfonso.usersapp.data.modules.users.model.UsersDataModel
import com.alfonso.usersapp.domain.modules.users.model.UsersDomainModel

fun UsersDataModel.toDomain() =
    UsersDomainModel(
        userId = userId?: -1,
        id = id?: -1,
        title = title?: "",
        completed = completed?: false
    )