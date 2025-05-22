package com.alfonso.usersapp.domain.modules.users.usecase

import com.alfonso.usersapp.domain.modules.users.model.UsersDomainModel
import com.alfonso.usersapp.domain.modules.users.repository.IUsersRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

/**
 * Test for get all users from cache
 */
class GetLocalUsersUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: IUsersRepository

    lateinit var getLocalUsersUseCase: GetLocalUsersUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getLocalUsersUseCase = GetLocalUsersUseCase(repository)
    }

    @Test
    fun `when getAllLocalUsers is called then return local users list`() =  runBlocking {
        // Given
        val users = listOf(
            UsersDomainModel(1, 101, "Usuario 1", true),
            UsersDomainModel(2, 102, "Usuario 2", false)
        )

        coEvery { repository.getAllLocalUsers() } returns users

        // When
        val result = getLocalUsersUseCase()

        // Then
        assertEquals(users, result)
    }

    @Test
    fun `when getAllLocalUsers is called then return users empty`() = runBlocking {
        //Given
        coEvery { repository.getAllLocalUsers() } returns emptyList()
        //When
        val result = getLocalUsersUseCase()
        //Then
        assertTrue { result.isEmpty() }
    }

}