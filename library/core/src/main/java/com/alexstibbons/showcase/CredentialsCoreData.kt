package com.alexstibbons.showcase

import androidx.annotation.StringRes
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import java.util.regex.Pattern


typealias Validator = (String) -> Boolean

enum class LoginDataPoint {
    EMAIL, PASSWORD;
}

private val EMAIL_REGEX: String =
    "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"

val String.isEmailValid: Boolean get() {

    return Pattern.matches(EMAIL_REGEX, this)
}

val String.isPasswordValid: Boolean get() = (this.length > 5 && this.any { it.isDigit() })

val passVallidator: Validator = { pass -> pass.length > 5 && pass.any { it.isDigit() }}
val emailValidator: Validator = { email ->  Pattern.matches(EMAIL_REGEX, email)}

fun TextInputEditText.onAfterCredentialsInput(@StringRes errorString: Int, dataPoint: LoginDataPoint, action: (text: String) -> Unit) {
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

val LoginDataPoint.validator: Validator get() = when (this) {
    LoginDataPoint.EMAIL -> emailValidator
    LoginDataPoint.PASSWORD -> passVallidator
}.exhaustive