package com.alfonso.usersapp.domain.modules.users.usecase

import com.alfonso.usersapp.domain.modules.users.model.UsersDomainModel
import com.alfonso.usersapp.domain.modules.users.repository.IUsersRepository
import javax.inject.Inject

class GetLocalUsersUseCase @Inject constructor(
    private val repository: IUsersRepository
) {
    suspend operator fun invoke(): List<UsersDomainModel> =
        repository.getAllLocalUsers()
}