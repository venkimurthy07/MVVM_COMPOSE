package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.ApiResponse
import com.example.myapplication.domain.usecase.GetAllBreedsUseCase
import com.example.myapplication.domain.usecase.GetRandomDogImageUseCase
import com.example.myapplication.domain.util.Result
import kotlinx.coroutines.launch

class MainViewModel(
    private val getRandomDogImageUseCase: GetRandomDogImageUseCase,
    private val getAllBreedsUseCase: GetAllBreedsUseCase
) : ViewModel() {

    private val _dogImage = MutableLiveData<Result<ApiResponse<String>>>()
    val dogImage: LiveData<Result<ApiResponse<String>>> = _dogImage

    private val _allBreeds = MutableLiveData<Result<ApiResponse<Map<String, List<String>>>>>()
    val allBreeds: LiveData<Result<ApiResponse<Map<String, List<String>>>>> = _allBreeds

    fun fetchRandomDogImage() {
        viewModelScope.launch {
            _dogImage.value = Result.Loading
            _dogImage.value = getRandomDogImageUseCase()
        }
    }

    fun fetchAllBreeds() {
        viewModelScope.launch {
            _allBreeds.value = Result.Loading
            _allBreeds.value = getAllBreedsUseCase()
        }
    }
}
