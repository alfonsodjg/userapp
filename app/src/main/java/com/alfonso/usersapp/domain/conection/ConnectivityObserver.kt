package com.alfonso.usersapp.domain.conection

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe(): Flow<NetworkStatus>
}