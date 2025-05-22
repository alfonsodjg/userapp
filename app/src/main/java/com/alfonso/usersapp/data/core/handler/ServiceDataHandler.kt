package com.alfonso.usersapp.data.core.handler

sealed class ServiceDataHandler<out T: Any> {
    data class Success<out T : Any>(val data: T) : ServiceDataHandler<T>()
    data class Error(val exception: ServiceErrorData) : ServiceDataHandler<Nothing>()
}
sealed class ServiceErrorData(
    val code: Int,
    val message: String,
    val description: String
) {
    class RedirectError(
        code: Int,
        message: String,
        description: String
    ) : ServiceErrorData(code, message, description)

    class AuthError(
        code: Int,
        message: String,
        description: String
    ): ServiceErrorData(code, message, description)

    class ServerError(
        code: Int,
        message: String,
        description: String
    ): ServiceErrorData(code, message, description)

    class ClientError(
        code: Int,
        message: String,
        description: String
    ): ServiceErrorData(code, message, description)

    class UnknownHostException(
        code: Int,
        message: String,
        description: String
    ): ServiceErrorData(code, message, description)

    class CastException(
        code: Int,
        message: String,
        description: String
    ): ServiceErrorData(code, message, description)

    class UnknownError(
        code: Int,
        message: String,
        description: String
    ): ServiceErrorData(code, message, description)
}