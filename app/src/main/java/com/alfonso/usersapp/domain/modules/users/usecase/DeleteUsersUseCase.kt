package com.alfonso.usersapp.domain.modules.users.usecase

import com.alfonso.usersapp.domain.modules.users.repository.IUsersRepository
import javax.inject.Inject

/**
 * Use case for delete all Users in the room
 */
class DeleteUsersUseCase @Inject constructor(
    private val repository: IUsersRepository
) {
    suspend operator fun invoke() = repository.deleteAllUsers()
}