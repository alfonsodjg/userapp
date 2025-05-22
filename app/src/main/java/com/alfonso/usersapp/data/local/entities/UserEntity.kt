package com.alfonso.usersapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class UserEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "user_id") val userId: Int?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "completed") val completed: Boolean?
)
