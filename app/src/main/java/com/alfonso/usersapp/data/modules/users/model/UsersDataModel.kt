package com.alfonso.usersapp.data.modules.users.model

import com.google.gson.annotations.SerializedName

data class UsersDataModel(
    @SerializedName("userId")
    val userId: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("completed")
    val completed: Boolean?
)
