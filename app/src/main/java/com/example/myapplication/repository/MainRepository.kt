package com.example.myapplication.repository

import com.example.myapplication.network.ApiService

class MainRepository(private val apiService: ApiService) {

    suspend fun getRandomDogImage() = apiService.getRandomDogImage()

    suspend fun getAllBreeds() = apiService.getAllBreeds()
}
