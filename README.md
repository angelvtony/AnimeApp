📺 Anime Explorer App (Android)

An Android application that fetches and displays anime data using the Jikan API (MyAnimeList unofficial API).
The app showcases popular anime, detailed information, trailer playback, offline support, and clean architecture using MVVM.

🚀 Objective

Build a simple yet robust Android app that:

Fetches anime data from the Jikan API

Displays a list of top-rated anime

Shows detailed anime information with trailers

Works seamlessly online and offline

Follows clean architecture and best practices

🧩 Features
🏠 Anime List (Home Screen)

Fetches top anime from the Jikan API

Displays:

Anime Title

Number of Episodes

Rating (MyAnimeList score)

Poster Image

Smooth scrolling with efficient image loading

API Used:

https://api.jikan.moe/v4/top/anime

📄 Anime Detail Page

Opens on anime selection

Displays:

Trailer video (if available)
(Falls back to poster image if trailer is unavailable)

Title

Plot / Synopsis

Genre(s)

Main Cast

Total Episodes

Rating

API Used:

https://api.jikan.moe/v4/anime/{anime_id}

💾 Offline Support (Room Database)

Stores fetched anime data locally using Room

Allows users to browse previously loaded anime without internet

Automatically syncs data when the device goes online

🌐 Offline Mode & Syncing

Detects network changes

Loads data from local database when offline

Refreshes data from API when internet is available

⚠️ Error Handling

Gracefully handles:

API failures

Network connectivity issues

Empty or partial responses

Database read/write errors

User-friendly messages are shown instead of crashes.

🧱 Architecture & Design Patterns

https://github.com/user-attachments/assets/52b277f8-0fb3-4a10-836e-c8fc80b66096



MVVM (Model–View–ViewModel)

Clean separation of concerns

Repository pattern for data management

📚 Libraries Used
Purpose	Library
Networking	Retrofit
Image Loading	Glide / Picasso
Local Database	Room
Reactive Data	LiveData / StateFlow
Video Playback	ExoPlayer
JSON Parsing	Gson / Moshi
🎨 UI/UX & Constraint Handling

Responsive and intuitive UI

Designed to handle layout changes gracefully

Profile images can be removed due to legal constraints without breaking UI structure

Clean fallback states for missing content

🧠 Problem Solving & Edge Case Handling

Network resilience

Offline-first approach

Safe null handling for API responses

Trailer availability checks

Clean UI fallback for missing images or data

🛠 Tech Stack

Language: Kotlin

Architecture: MVVM


📌 Future Enhancements

Search anime by name

Pagination support

Favorite anime feature


🧑‍💻 Author

Angel Tony
Android Developer | Kotlin | MVVM

📜 License

This project is for learning and demonstration purposes.
