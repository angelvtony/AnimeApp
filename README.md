# Anime App

A simple Android application that fetches and displays anime data using the Jikan API.

## Features

- **Top Anime List**: Displays a list of top-rated anime with infinite scrolling (pagination not implemented for simplicity, just top list).
- **Anime Details**: Shows detailed information including synopsis, rating, episodes, and genres.
- **Trailer**: Plays the anime trailer (if available) using a WebView.
- **Cast**: Displays the main cast members.
- **Offline Support**: Caches data locally using Room database. Works offline after the first successful fetch.
- **Modern Architecture**: Built with MVVM, Clean Architecture, Hilt, Coroutines, Flow, and Jetpack Navigation.

## Tech Stack

- **Language**: Kotlin
- **UI**: XML Layouts, ViewBinding, Material Design 3
- **Architecture**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: Hilt
- **Network**: Retrofit, OkHttp, Gson
- **Image Loading**: Glide
- **Database**: Room
- **Async**: Coroutines, Flow
- **Navigation**: Jetpack Navigation Component
