package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.ApiResponse
import com.example.myapplication.domain.util.Result
import com.example.myapplication.repository.MainRepository

class GetAllBreedsUseCase(private val repository: MainRepository) {
    suspend operator fun invoke(): Result<ApiResponse<Map<String, List<String>>>> {
        return try {
            val response = repository.getAllBreeds()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body)
                } else {
                    Result.Error(Exception("Response body is null"))
                }
            } else {
                Result.Error(Exception("API Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
