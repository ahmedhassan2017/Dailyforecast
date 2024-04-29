package com.example.baimsdailyforecast.data.remote

/**
 * API request state.
 */
enum class State {
    Loading,
    Done,
    ApiError,
    NetworkError,
}