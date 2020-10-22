package com.alexstibbons.showcase.network

import java.net.HttpURLConnection

/**
 * @author N. Vuksic github @ Vuksa
 * */

sealed class NetworkResponse<out R>(val code: Int) {

    data class SuccessResponse<out R>(
        val value: R,
        private val responseCode: Int
    ): NetworkResponse<R>(responseCode)

    data class EmptyBodySuccess<out R>(
        private val responseCode: Int
    ): NetworkResponse<R>(responseCode)

    data class ErrorResponse(
        val message: String,
        private val errorCode: Int
    ): NetworkResponse<Nothing>(errorCode) {
        val isBadRequestError: Boolean get() = errorCode == HttpURLConnection.HTTP_BAD_REQUEST
        val isServerError: Boolean get() = errorCode == HttpURLConnection.HTTP_INTERNAL_ERROR
    }

    val isSuccess get() = this is SuccessResponse<R>

    val isFailure get() = this is ErrorResponse

    companion object {
        fun <R> retrofit2.Response<R>.parseResponse(): NetworkResponse<R> {
            val response = this
            return if (response.isSuccessful) {
                val code = response.code()
                when {
                    response.body() == null || code == HttpURLConnection.HTTP_NO_CONTENT -> {
                        EmptyBodySuccess(
                            code
                        )
                    }
                    else -> SuccessResponse(
                        response.body()!!,
                        response.code()
                    )
                }
            } else {
                val msg = response.errorBody()?.string() ?: response.message()

                ErrorResponse(
                    msg,
                    response.code()
                )
            }
        }
    }
}