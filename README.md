# My Application - Project Overview

This document provides an overview of the project architecture and the responsibilities of each major class.

## Architecture

This project follows a modern Android architecture based on Google's recommendations, incorporating principles from MVVM (Model-View-ViewModel) and Clean Architecture.

- **UI Layer**: Displays the application data on the screen and handles user interaction. (Activity, Composables, ViewModel).
- **Domain Layer**: Contains the core business logic of the application. (Use Cases).
- **Data Layer**: Exposes data to the rest of the app from one or more sources (e.g., network, local database). (Repository, Models, API Service).
- **Dependency Injection**: Koin is used to manage dependencies between classes, making the code more modular and testable.

---

## Class Responsibilities

Here is a breakdown of each class and its role in the application:

### Application

- **`MyApplication.kt`**: The main entry point of the Android application. Its primary responsibility is to initialize the Koin dependency injection framework when the app starts.

### Dependency Injection (`di`)

- **`AppModule.kt`**: Defines how Koin should create and provide dependencies. It maps interfaces to implementations and defines the lifecycle of objects (e.g., `single` for singletons, `viewModel` for ViewModels).

### Network Layer (`network`)

- **`ApiClient.kt`**: A singleton object responsible for creating and configuring the `Retrofit` instance. It sets the base URL for the API and adds necessary components like the Gson converter and a logging interceptor.
- **`ApiService.kt`**: A Retrofit interface that defines the API endpoints. It uses annotations (`@GET`, `@POST`, etc.) to describe the HTTP requests, paths, and the expected request/response models.

### Data Layer (`data` & `repository`)

- **`data/model/ApiResponse.kt`**: A generic data class representing the standard JSON response structure from the Dog API. It can wrap different types of data (`String`, `Map`) found in the `"message"` field, making it highly reusable.
- **`repository/MainRepository.kt`**: Acts as the single source of truth for data. It abstracts the data source from the rest of the app. Its job is to call the `ApiService` to fetch network data. In a larger app, it might also decide whether to fetch from the network or a local cache.

### Domain Layer (`domain`)

- **`domain/util/Result.kt`**: A generic sealed class used to wrap the outcome of data operations. It provides a clean and robust way to represent `Loading`, `Success`, and `Error` states throughout the application.
- **`domain/usecase/GetRandomDogImageUseCase.kt`**: A use case containing the specific business logic for fetching a random dog image. It calls the repository and wraps the result in the `Result` class.
- **`domain/usecase/GetAllBreedsUseCase.kt`**: A use case containing the business logic for fetching and filtering the list of all dog breeds. It takes the raw data from the repository, applies business rules (like filtering by a query), and returns the processed data.

### UI Layer (`viewmodel`, `MainActivity.kt`)

- **`viewmodel/MainViewModel.kt`**: A ViewModel that acts as the bridge between the business logic (Use Cases) and the UI (`MainActivity`). It holds the UI state, calls the use cases, and exposes the data as `LiveData` for the UI to observe and react to.
- **`MainActivity.kt`**: The main and only screen of the app. It's responsible for:
    - Getting the `MainViewModel` instance from Koin.
    - Observing the `LiveData` state from the ViewModel.
    - Displaying the UI based on the current state (`Loading`, `Success`, `Error`).
    - Triggering data fetches (e.g., in a `LaunchedEffect`).
