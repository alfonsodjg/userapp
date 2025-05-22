package com.alfonso.usersapp.domain.modules.users.usecase

import com.alfonso.usersapp.domain.modules.users.model.UsersDomainModel
import com.alfonso.usersapp.domain.modules.users.repository.IUsersRepository
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import kotlinx.coroutines.runBlocking
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Test for update user
 *
 */
class UpdateUserUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: IUsersRepository

    lateinit var updateUserUseCase: UpdateUserUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        updateUserUseCase = UpdateUserUseCase(repository)
    }

    @Test
    fun `when a user was updated`() = runBlocking {
        //Given
        val user = UsersDomainModel(
            userId = 1,
            id = 100,
            title = "User 1",
            completed = false
        )
        coEvery { repository.updateUser(user) } just Runs

        //When
        val response = updateUserUseCase(user)

        //Then
        coVerify(exactly = 1) { repository.updateUser(user) }
        assertEquals(Unit, response)

    }

}