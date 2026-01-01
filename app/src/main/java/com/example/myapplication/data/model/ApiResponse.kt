package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

/**
 * A generic class to handle the standard response structure from the dog.ceo API.
 * @param <T> The type of the 'message' field, which can be a String, List, or Map.
 */
data class ApiResponse<T>(
    @SerializedName("message")
    val message: T,

    @SerializedName("status")
    val status: String
)
