package com.example.baimsdailyforecast.data.remote

import com.example.baimsdailyforecast.data.remote.responses.ErrorResponse
import retrofit2.Response
import java.lang.Exception

/**
 * API errors helper class
 */
class ErrorParser {
    companion object {
        /**
         * Parse error responses from the API.
         */
        fun parseError(response: Response<*>,baseUrl:String): ErrorResponse
        {
            val converter = ApiClient().retrofit(baseUrl)
                .responseBodyConverter<ErrorResponse>(ErrorResponse::class.java, emptyArray())

            val errorResponse =  try {
                converter.convert(response.errorBody()!!)!!
            } catch (exception: Exception) {
                ErrorResponse("Unknown error")
            }

            return if (errorResponse.message.isBlank() && errorResponse.errors.isEmpty()) {
                ErrorResponse("Unknown error", errorResponse.errors)
            } else {
                errorResponse
            }
        }
    }
}