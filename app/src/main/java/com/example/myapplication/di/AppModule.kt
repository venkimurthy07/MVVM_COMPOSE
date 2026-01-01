package com.example.myapplication.di

import com.example.myapplication.domain.usecase.GetAllBreedsUseCase
import com.example.myapplication.domain.usecase.GetRandomDogImageUseCase
import com.example.myapplication.network.ApiClient
import com.example.myapplication.repository.MainRepository
import com.example.myapplication.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    single { ApiClient.instance }
    singleOf(::MainRepository)
    singleOf(::GetRandomDogImageUseCase)
    singleOf(::GetAllBreedsUseCase)
    viewModelOf(::MainViewModel)
}
