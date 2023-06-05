# LightningForecast
A Simple Clean Architecture Weather App that strikes⚡

## Overview
LightningForecast is an Android application that provides users with current weather information for their current location. It also offers a 10-day weather forecast. The app is designed with a clean architecture approach, making it scalable and maintainable.

## Features
- Fetches the user's current location and determines their current place.
- Displays detailed information about the current weather, including humidity, wind speed, and precipitation probability.
- Provides an hourly forecast for the next 24 hours with the same detailed weather information.
- Shows a daily forecast with details such as sunrise and sunset times, wind speed, and the maximum and minimum temperatures for the day.
- Allows users to view the weather details for any of the next 10 days.
- Utilizes Lottie animations instead of simple SVG/PNG icons to enhance the visual experience.
- Implements the Material 3 color palette for a modern and visually appealing interface.

## Technologies and Approaches
- **Kotlin 100%**: The entire application is written in Kotlin, harnessing its modern features and advantages.
- **No XML layouts**: Instead of using XML layouts, the app leverages the power of Jetpack Compose.
- **Clean Architecture**: The app follows the principles of Clean Architecture.
- **MVI Pattern**: LightningForecast adopts the Model-View-Intent (MVI) pattern, which provides a unidirectional data flow and a clear separation of state and UI logic.
- **Dependency Injection**: The Koin library is used for dependency injection. It is pretty easy to use, understand and implement, especially for small projects.
- **Kotlin Coroutines**: The app utilizes Kotlin Coroutines for asynchronous and non-blocking programming, enhancing performance and responsiveness.
- **REST API Interaction**: LightningForecast communicates with a REST API to retrieve weather data, providing users with up-to-date information.

## Libraries and Frameworks Used
- **Jetpack Compose**: The modern UI toolkit that enables building beautiful and responsive user interfaces.
- **Kotlin Coroutines**: The Kotlin library for asynchronous programming and handling concurrency in a simplified manner.
- **Kotlin Serialization**: A library for serializing Kotlin objects to and from JSON.
- **Ktor**: A 100% Kotlin networking library used for making HTTP requests and interacting with the REST API regularly updated and with a bunch of additional and convenient functionality.
- **Koin**: A lightweight dependency injection framework for Kotlin.
- **OrbitMVI**: A library that facilitates the implementation of the MVI pattern in Android applications.
- **Lottie**: A library for adding .json based interactive animations to the user interface.
- **GMS Play services**: The Google Play services library that provides location-related functionality.
- **Splash screen**: A feature that displays an initial loading screen with branding elements while the app is loading.