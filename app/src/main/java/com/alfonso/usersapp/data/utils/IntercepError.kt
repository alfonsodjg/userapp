package com.alfonso.usersapp.data.utils

import com.alfonso.usersapp.data.core.handler.ServiceErrorData
import retrofit2.HttpException
import java.net.UnknownHostException
import java.io.IOException
import com.google.gson.JsonSyntaxException

fun Exception.castErrorToServiceDataError(): ServiceErrorData {
    return when (this) {
        is HttpException -> {
            val code = this.code()
            val message = this.message()
            val description = this.response()?.errorBody()?.string() ?: "No description"

            when (code) {
                in 300..399 -> ServiceErrorData.RedirectError(code, message, description)
                in 400..499 -> ServiceErrorData.ClientError(code, message, description)
                in 500..599 -> ServiceErrorData.ServerError(code, message, description)
                else -> ServiceErrorData.UnknownError(code, message, description)
            }
        }

        is UnknownHostException -> {
            ServiceErrorData.UnknownHostException(
                code = 0,
                message = this.message ?: "Unknown host",
                description = this.toString()
            )
        }

        is JsonSyntaxException -> {
            ServiceErrorData.CastException(
                code = 0,
                message = this.message ?: "Parsing error",
                description = this.toString()
            )
        }

        is IOException -> {
            ServiceErrorData.UnknownHostException(
                code = 0,
                message = this.message ?: "Network error",
                description = this.toString()
            )
        }

        else -> {
            ServiceErrorData.UnknownError(
                code = 0,
                message = this.message ?: "Unknown error",
                description = this.toString()
            )
        }
    }
}