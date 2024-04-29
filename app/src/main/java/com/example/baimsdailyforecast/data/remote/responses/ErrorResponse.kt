package com.example.baimsdailyforecast.data.remote.responses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Error response data holder.
 */
@Parcelize
data class ErrorResponse(val message: String, var errors: Map<String, List<String>> = emptyMap()): Parcelable