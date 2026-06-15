# UB Quiz

UB Quiz is a mobile quiz game built with Jetpack Compose.

The application uses Cloudflare D1, a serverless SQLite database, to store user profiles, quiz levels, categories, questions, answers, scores, and game history.

## Features

- Multiple quiz levels and categories
- Countdown timer
- Three-life game system
- Score and progress tracking
- Answer history
- User profile and performance statistics
- Modern UI built with Jetpack Compose
- MVVM architecture

## Tech Stack

### Android

- Kotlin
- Jetpack Compose
- Material 3
- MVVM
- Retrofit
- Kotlin Coroutines
- StateFlow
- DataStore

### Backend

- Next.js API Routes
- Cloudflare Workers
- Cloudflare D1
- SQLite

## Database

Cloudflare D1 is used as the primary database. D1 is a serverless SQL database built on SQLite.

The database includes the following tables:

- `Profile`
- `Level`
- `LevelPass`
- `Category`
- `Question`
- `Answer`
- `History`
- `Point`

## Architecture

The Android application follows the MVVM architecture:

- **UI:** Jetpack Compose screens and reusable components
- **ViewModel:** Manages UI state, timer, lives, questions, and score
- **Repository:** Handles communication with the backend API
- **API:** Next.js endpoints deployed through Cloudflare
- **Database:** Cloudflare D1 SQLite database
