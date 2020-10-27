package com.alexstibbons.showcase.responses

import com.alexstibbons.showcase.exhaustive

val EMPTY_SUCCESS =
    Response.success(Unit)

sealed class Response<out F: Failure, out S: Any> {

    data class Failure<out F : com.alexstibbons.showcase.responses.Failure>(val failure: F): Response<F, Nothing>()

    data class Success<out S: Any>(val success: S): Response<Nothing, S>()

    val isSuccess get() = this is Success<S>

    val isFailure get() = this is Failure<F>

    companion object {

        fun <F: com.alexstibbons.showcase.responses.Failure> failure(f: F) =
            Failure(f)

        fun <S: Any> success(s: S) =
            Success(s)
    }

    fun doOnSuccess(fn: (S: Any) -> Unit) {
        if (this is Success) fn(success)
    }

    fun doOnFailure(fn: (F: com.alexstibbons.showcase.responses.Failure)-> Unit) {
        if (this is Failure) fn(failure)
    }
}

fun <F: Failure, S: Any> Response<F, S>.getSuccessOrElse(value: S): S = when (this) {
    is Response.Failure -> value
    is Response.Success -> this.success
}.exhaustive


fun <F: Failure, S: Any> Response<F, S>.getSuccessOrNull(): S? = when (this) {
    is Response.Failure -> null
    is Response.Success -> this.success
}.exhaustive

fun <F: Failure, S: Any> Response<F, S>.getSuccessOrThrow(message: String = "Response failed with: "): S = when (this) {
    is Response.Failure -> throw error("$message ${this.failure}")
    is Response.Success -> this.success
}.exhaustive

fun <F: Failure, S: Any, T: Any> Response<F, S>.mapper(onSuccess: (S) -> (T)): Response<F, T> {
    return when (this) {
        is Response.Failure -> Response.failure(this.failure)
        is Response.Success -> Response.success(onSuccess(this.success))
    }.exhaustive
}
