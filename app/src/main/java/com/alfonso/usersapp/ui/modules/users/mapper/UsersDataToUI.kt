package com.alfonso.usersapp.ui.modules.users.mapper

import com.alfonso.usersapp.domain.modules.users.model.UsersDomainModel
import com.alfonso.usersapp.ui.modules.users.model.UsersUIModel

fun UsersDomainModel.toUI() =
    UsersUIModel(
        userId = userId,
        id = id,
        title = title,
        completed = completed
    )

fun UsersUIModel.toDomain(): UsersDomainModel {
    return UsersDomainModel(
        userId = userId,
        id = id,
        title = title,
        completed = completed
    )
}