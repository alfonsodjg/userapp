package com.alfonso.usersapp.data.local.mapper

import com.alfonso.usersapp.data.local.entities.UserEntity
import com.alfonso.usersapp.domain.modules.users.model.UsersDomainModel

fun UserEntity.toDomain() = UsersDomainModel(
    userId = userId ?: -1,
    id = id,
    title = title ?: "",
    completed = completed ?: false
)

fun UsersDomainModel.toEntity() = UserEntity(
    id = id,
    userId = userId,
    title = title,
    completed = completed
)