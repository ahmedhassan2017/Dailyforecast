package com.example.baimsdailyforecast.data.remote

import com.example.baimsdailyforecast.data.remote.responses.ErrorResponse
import retrofit2.Call
import java.net.UnknownHostException
import retrofit2.Response as RetrofitResponse

/**
 * A wrapper for API responses to handle common situations.
 */
class ApiResponse<T>(var data: T? = null, var error: ErrorResponse? = null, var code: Int? = null, var state: State) {
    constructor(): this(state = State.Loading)
    constructor(data: T?): this(data, state = State.Done)
    constructor(throwable: Throwable): this() {
        val errorMessage = if (throwable.message.isNullOrBlank()) {
            "Something went wrong"
        } else if (throwable.message.toString().contains("Failed to connect")||
            throwable.message.toString().contains("timeout")) {
            "Check your internet connection"
        }
        else{
            throwable.message.toString()
        }

        error = ErrorResponse(errorMessage)
        state = State.NetworkError
    }
    constructor(retrofitResponse: RetrofitResponse<T>,baseUrl:String): this() {
        if (retrofitResponse.isSuccessful) {
            data = retrofitResponse.body()
            state = State.Done
            code = retrofitResponse.code()
        } else {
            error = ErrorParser.parseError(retrofitResponse,baseUrl)
            state = State.ApiError
            code = retrofitResponse.code()
        }
    }

    companion object {
        fun <T>forCall(call: Call<T>?,baseUrl:String): ApiResponse<T>
        {
            return try {
                ApiResponse(call?.execute()!!,baseUrl)
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException ->
                        ApiResponse(java.lang.Exception("Internet connection not available"))
                    else -> {
                        ApiResponse(e)
                    }

                }
            }
        }

    }

    fun isSuccess(): Boolean {
        return error == null
    }
}