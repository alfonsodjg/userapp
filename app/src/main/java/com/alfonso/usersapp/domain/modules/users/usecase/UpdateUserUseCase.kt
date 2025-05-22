package com.alfonso.usersapp.domain.modules.users.usecase

import com.alfonso.usersapp.domain.modules.users.model.UsersDomainModel
import com.alfonso.usersapp.domain.modules.users.repository.IUsersRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val repository: IUsersRepository
) {
    suspend operator fun invoke(user: UsersDomainModel) = repository.updateUser(user)
}