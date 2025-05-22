package com.alfonso.usersapp.domain.modules.users.usecase

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

/**
 * Test for delete all users
 */
class DeleteUsersUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: IUsersRepository

    lateinit var deleteUsersUseCase: DeleteUsersUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        deleteUsersUseCase = DeleteUsersUseCase(repository)
    }

    @Test
    fun `when all users was deleted`() = runBlocking {
        //Given
        coEvery { repository.deleteAllUsers() } just Runs

        //When
        deleteUsersUseCase()

        // Then
        coVerify(exactly = 1) { repository.deleteAllUsers() }
    }

}