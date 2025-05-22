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
import kotlin.test.assertTrue

/**
 * Test for delete user
 */
class DeleteUserUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: IUsersRepository

    lateinit var deleteUsersUseCase: DeleteUserUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        deleteUsersUseCase = DeleteUserUseCase(repository)
    }

    @Test
    fun `when a user was deleted` () = runBlocking {
        //Given
        val user = UsersDomainModel(
            userId = 1,
            id = 100,
            title = "User 1",
            completed = false
        )
        coEvery { repository.deleteUser(user) } just Runs
        //When

        val response = deleteUsersUseCase(user)

        //Then
        coVerify(exactly = 1) { repository.deleteUser(user) }
        assertEquals(Unit, response)
    }
}