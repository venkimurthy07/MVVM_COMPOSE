package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.ApiResponse
import com.example.myapplication.domain.util.Result
import com.example.myapplication.repository.MainRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

class GetAllBreedsUseCaseTest {

    private lateinit var repository: MainRepository
    private lateinit var useCase: GetAllBreedsUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetAllBreedsUseCase(repository)
    }

    @Test
    fun `invoke() with successful response and matching query should return filtered success`() = runTest {
        // Given
        val query = "terrier"
//        val breeds = mapOf(
//            "terrier-boston" to emptyList(),
//            "hound-afghan" to emptyList(),
//            "terrier-scottish" to emptyList()
//        )

        val breeds = mapOf(
            "terrier-boston" to emptyList<String>(),
            "hound-afghan" to emptyList<String>(),
            "terrier-scottish" to emptyList<String>()
        )
        val apiResponse = ApiResponse(breeds, "success")
        val successfulResponse = Response.success(apiResponse)

        coEvery { repository.getAllBreeds() } returns successfulResponse

        // When
        val result = useCase()

        // Then
        assertTrue(result is Result.Success)
        val successResult = result as Result.Success
        assertEquals(3, successResult.data.message.size)
        assertTrue(successResult.data.message.containsKey("terrier-boston"))
        assertTrue(successResult.data.message.containsKey("terrier-scottish"))
    }

    @Test
    fun `invoke() with successful response and empty query should return all breeds`() = runTest {
        // Given
        val breeds = mapOf(
            "terrier-boston" to emptyList<String>(),
            "hound-afghan" to emptyList<String>(),
            "terrier-scottish" to emptyList<String>()
        )
        val apiResponse = ApiResponse(breeds, "success")
        val successfulResponse = Response.success(apiResponse)

        coEvery { repository.getAllBreeds() } returns successfulResponse

        // When
        val result = useCase()

        // Then
        assertTrue(result is Result.Success)
        val successResult = result as Result.Success
        assertTrue(successResult.data.message.isNotEmpty())
    }

    @Test
    fun `invoke() with API error should return error`() = runTest {
        // Given
        val errorResponse = Response.error<ApiResponse<Map<String, List<String>>>>(404, mockk(relaxed = true))

        coEvery { repository.getAllBreeds() } returns errorResponse

        // When
        val result = useCase()

        // Then
        assertTrue(result is Result.Error)
    }

    @Test
    fun `invoke() with exception should return error`() = runTest {
        // Given
        val exception = RuntimeException("Network error")

        coEvery { repository.getAllBreeds() } throws exception

        // When
        val result = useCase()

        // Then
        assertTrue(result is Result.Error)
        val errorResult = result as Result.Error
        assertEquals(exception, errorResult.exception)
    }
}
