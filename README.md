# Real Estate Listings App
## About
A modern Android application built with **Clean Architecture** and **MVI** pattern to display real estate property listings.

## Architecture

The project follows **Clean Architecture** principles with clear separation of concerns across three layers:

### Data Layer
- Handles all data operations and API communication
- Repository implementations
- Remote data sources
- Network data sources with Retrofit
- Data models (DTOs) and mappers
- Has access to the ```domain``` module

### Domain Layer
- Contains Repository interfaces and business object models - BOs,
- Result wrapper for handling success/error/loading states
- Pure Kotlin module with no Android dependencies

### Presentation Layer
- **MVI (Model-View-Intent)** pattern for state management
- Jetpack Compose UI
- ViewModels with Intent handling
- Unidirectional data flow with StateFlow and SharedFlow
- Jetpack Navigation
- Has access to the ```domain``` module

### Module Structure
```
├── app/              # Application entry point and DI setup
├── data/             # API, repositories implementation, local or remote data sources
├── domain/           # Business models and repository interfaces
└── presentation/     # UI screens, ViewModels, navigation
```
## Libraries

- **[Jetpack Compose](https://developer.android.com/jetpack/compose)** - Modern declarative UI
- **[Material3](https://m3.material.io/)** - Material Design components
- **[Koin](https://insert-koin.io/)** - Dependency injection
- **[Retrofit](https://square.github.io/retrofit/)** - REST API client
- **[OkHttp](https://square.github.io/okhttp/)** - HTTP client
- **[Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)** - JSON serialization
- **[Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)** - Asynchronous programming
- **[Coil](https://coil-kt.github.io/coil/)** - Image loading
- **[Navigation Compose](https://developer.android.com/jetpack/compose/navigation)** - Screen navigation

## Testing

The project includes comprehensive unit tests covering critical components:

- **Mapper Tests** - Verify DTO to domain model transformations with edge cases
- **Repository Tests** - Test data flow with success/error scenarios using MockK
- **ViewModel Tests** - Validate MVI state management and event emissions with Turbine
- **Flow Testing** - Asynchronous Flow testing with Coroutines Test utilities

### Testing Libraries
- **[JUnit](https://junit.org/junit4/)** - Unit testing framework
- **[MockK](https://mockk.io/)** - Mocking for Kotlin
- **[Turbine](https://github.com/cashapp/turbine)** - Flow testing
- **[Coroutines Test](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-test/)** - Coroutine testing utilities
