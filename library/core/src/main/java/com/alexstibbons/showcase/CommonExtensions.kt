package com.alexstibbons.showcase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * misc extensions
 * */
val <T> T.exhaustive: T
    get() = this

fun <T> T?.doWhenNull(fn: () -> Unit) = this ?: fn()

inline fun <reified T : Any> simpleName() = T::class.java.simpleName

/**
 * activity extensions
 * */
inline fun <reified T: Activity> Context.startActivity() {
    this.startActivity(Intent(this, T::class.java))
}

inline fun Context.showToast(message: String) {
    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
}

inline fun Context.showToast(@StringRes messageRes: Int) {
    Toast.makeText(applicationContext, messageRes, Toast.LENGTH_LONG).show()
}

/**
 * metric extensions
 * */
fun Int.toPx(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Int.toDp(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()