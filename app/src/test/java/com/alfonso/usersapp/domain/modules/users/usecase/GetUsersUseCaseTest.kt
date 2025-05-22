package com.alfonso.usersapp.domain.modules.users.usecase

import com.alfonso.usersapp.domain.core.handler.ServiceDomainHandler
import com.alfonso.usersapp.domain.core.handler.ServiceErrorDomain
import com.alfonso.usersapp.domain.modules.users.model.UsersDomainModel
import com.alfonso.usersapp.domain.modules.users.repository.IUsersRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Test for get all users from internet
 */
class GetUsersUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: IUsersRepository

    lateinit var getUsersUseCase: GetUsersUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getUsersUseCase = GetUsersUseCase(repository)
    }

    @Test
    fun `when getAllUsers from api is success and return a list`() = runBlocking {

        //Given
        val users = listOf(
            UsersDomainModel(1, 101, "User 1", true),
            UsersDomainModel(2,100, "User 2", false)
        )
        coEvery { repository.getAllUsers() } returns ServiceDomainHandler.Success(users)

        //When
        val response = getUsersUseCase()

        //Then
        assertTrue { response is ServiceDomainHandler.Success }
        assertEquals(users, (response as ServiceDomainHandler.Success).data )
    }

    @Test
    fun `when getAllUsers from api returns error`() = runBlocking {
        //Given
        val error = ServiceErrorDomain.UnknownError(
            code = 500,
            message = "Server Error",
            description = "Something went wrong"
        )
        coEvery { repository.getAllUsers() } returns ServiceDomainHandler.Error(error)

        //When
        val response = getUsersUseCase()
        //Then
        assertTrue(response is ServiceDomainHandler.Error)
        val exception = response.exception
        assertEquals(error.code, exception.code)
        assertEquals(error.message, exception.message)
        assertEquals(error.description, exception.description)
    }
}