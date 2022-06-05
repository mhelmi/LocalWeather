# Local Weather App

Local Weather app shows your current location weather and you can search for other cities weather
and save them for later check.

## How to run?

This project needs Android Studio 4.0.0 or above with Android Gradle plugin 7.0+

It's recommended to open it using <B>Android Studio Artic Fox</B> or above

## Architecture

Clean architecture based on MVVM (Model-View-ViewModel) with Coroutines , Flow, and StateFlow

The following diagram shows all the layers and how each layer interacts with each other. This
architecture uses a layered software architecture.
![MVVM](https://github.com/mhelmi/WeatherForecast/blob/master/art/mvvm_architecture.png)
![Clean Architecture](https://github.com/mhelmi/WeatherForecast/blob/master/art/clean_architecture.png)

## Built With 🛠

* [Kotlin](https://kotlinlang.org/) - official programming language for Android development.
* [Coroutines](https://developer.android.com/kotlin/coroutines) - for asynchronous or non-blocking
  programming.
* [Android Architecture Components](https://developer.android.com/jetpack/guide) - Part of Jetpack
  it's a set of libraries that help you design robust, testable, and maintainable apps.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and
    manage UI-related data in a lifecycle conscious way.
  - [Navigation](https://developer.android.com/guide/navigation) - Navigation refers to the
    interactions that allow users to navigate across, into, and back out from the different pieces
    of content within your app.
  - [Room](https://developer.android.com/training/data-storage/room) - persistence library provides
    an abstraction layer over SQLite to allow for more robust database access while harnessing the
    full power of SQLite.
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Hilt is a
  dependency injection library for Android that reduces the boilerplate of doing manual dependency
  injection (Based on Dagger 2).
* [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android.
* [Gson](https://github.com/google/gson) A Java serialization/deserialization library to convert
  Java Objects into JSON and back.
* [Material Design](https://material.io/design/guidelines-overview) are interactive building blocks
  for creating a friendly user interface.
* [Glide](https://github.com/bumptech/glide) An image loading and caching library.
* [JUnit](https://junit.org/junit5/) A foundation framework for developer-side testing on the JVM.
