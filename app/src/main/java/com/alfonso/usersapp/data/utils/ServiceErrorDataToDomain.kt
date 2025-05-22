package com.alfonso.usersapp.data.utils

import com.alfonso.usersapp.data.core.handler.ServiceErrorData
import com.alfonso.usersapp.domain.core.handler.ServiceErrorDomain

fun ServiceErrorData.toServiceErrorDomain(): ServiceErrorDomain {
    return when (this) {
        is ServiceErrorData.AuthError -> {
            ServiceErrorDomain.AuthError(
                this.code,
                this.message,
                this.description
            )
        }
        is ServiceErrorData.CastException -> {
            ServiceErrorDomain.CastException(
                this.code,
                this.message,
                this.description
            )
        }
        is ServiceErrorData.ClientError -> {
            ServiceErrorDomain.ClientError(
                this.code,
                this.message,
                this.description
            )
        }
        is ServiceErrorData.RedirectError -> {
            ServiceErrorDomain.RedirectError(
                this.code,
                this.message,
                this.description
            )
        }
        is ServiceErrorData.ServerError -> {
            ServiceErrorDomain.ServerError(
                this.code,
                this.message,
                this.description
            )
        }
        is ServiceErrorData.UnknownError -> {
            ServiceErrorDomain.UnknownError(
                this.code,
                this.message,
                this.description
            )
        }
        is ServiceErrorData.UnknownHostException -> {
            ServiceErrorDomain.UnknownHostException(
                this.code,
                this.message,
                this.description
            )
        }
    }
}