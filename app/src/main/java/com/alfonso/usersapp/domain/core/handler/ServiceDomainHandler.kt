package com.alfonso.usersapp.domain.core.handler

sealed class ServiceDomainHandler<out T: Any> {
    data class Success<out T : Any>(val data: T) : ServiceDomainHandler<T>()
    data class Error(val exception: ServiceErrorDomain) : ServiceDomainHandler<Nothing>()
}
sealed class ServiceErrorDomain(
    val code: Int,
    val message: String?,
    val description: String
) {
    class RedirectError(
        code: Int,
        message: String,
        description: String
    ) : ServiceErrorDomain(code, message, description)

    class AuthError(
        code: Int,
        message: String,
        description: String
    ): ServiceErrorDomain(code, message, description)

    class ServerError(
        code: Int,
        message: String,
        description: String
    ): ServiceErrorDomain(code, message, description)

    class ClientError(
        code: Int,
        message: String,
        description: String
    ): ServiceErrorDomain(code, message, description)

    class UnknownHostException(
        code: Int,
        message: String,
        description: String
    ): ServiceErrorDomain(code, message, description)

    class CastException(
        code: Int,
        message: String,
        description: String
    ): ServiceErrorDomain(code, message, description)

    class UnknownError(
        code: Int,
        message: String,
        description: String
    ): ServiceErrorDomain(code, message, description)
}