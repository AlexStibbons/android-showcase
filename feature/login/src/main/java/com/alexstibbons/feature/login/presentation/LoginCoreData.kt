package com.alexstibbons.feature.login.presentation

import androidx.annotation.StringRes
import androidx.core.widget.doAfterTextChanged
import com.alexstibbons.showcase.exhaustive
import com.google.android.material.textfield.TextInputEditText
import java.util.regex.Pattern

internal typealias Validator = (String) -> Boolean

internal enum class LoginDataPoint {
    EMAIL, PASSWORD;
}

private val EMAIL_REGEX: String =
    "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"

internal val String.isEmailValid: Boolean get() {

    return Pattern.matches(EMAIL_REGEX, this)
}

internal val String.isPasswordValid: Boolean get() = (this.length > 5 && this.any { it.isDigit() })

internal val passVallidator: Validator = { pass -> pass.length > 5 && pass.any { it.isDigit() }}
internal val emailValidator: Validator = { email ->  Pattern.matches(EMAIL_REGEX, email)}

internal fun TextInputEditText.onAfterCredentialsInput(@StringRes errorString: Int, dataPoint: LoginDataPoint, action: (text: String) -> Unit) {
    this.doAfterTextChanged { input ->
        val text = input.toString()
        action(text)
        if (!dataPoint.validator(text)) {
            this.error = this.context.getString(errorString)
        } else {
            this.error = null
        }
    }
}

internal val LoginDataPoint.validator: Validator get() = when (this) {
    LoginDataPoint.EMAIL -> emailValidator
    LoginDataPoint.PASSWORD -> passVallidator
}.exhaustive