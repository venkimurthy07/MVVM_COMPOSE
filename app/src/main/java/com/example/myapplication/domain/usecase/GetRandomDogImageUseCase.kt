package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.ApiResponse
import com.example.myapplication.domain.util.Result
import com.example.myapplication.repository.MainRepository

class GetRandomDogImageUseCase(private val repository: MainRepository) {
    suspend operator fun invoke(): Result<ApiResponse<String>> {
        return try {
            val response = repository.getRandomDogImage()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error(Exception("Response body is null"))
            } else {
                Result.Error(Exception("API Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
