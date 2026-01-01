package com.example.myapplication.network

import com.example.myapplication.data.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    /**
     * Fetches a random dog image. The 'message' field is a String.
     */
    @GET("api/breeds/image/random")
    suspend fun getRandomDogImage(): Response<ApiResponse<String>>

    /**
     * Fetches a list of all dog breeds. The 'message' field is a Map.
     */
    @GET("api/breeds/list/all")
    suspend fun getAllBreeds(): Response<ApiResponse<Map<String, List<String>>>>
}
