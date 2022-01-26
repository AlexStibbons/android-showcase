package com.alexstibbons.feature.login.presentation

import java.util.regex.Pattern

internal enum class LoginDataPoint {
    EMAIL, PASSWORD;
}

private val EMAIL_REGEX: String =
    "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"

internal val String.isEmailValid: Boolean get() {

    return Pattern.matches(EMAIL_REGEX, this)
}

internal val String.isPasswordValid: Boolean get() = (this.length > 5 && this.any { it.isDigit() })