package com.alexstibbons.showcase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * misc extensions
 * */
val <T> T.exhaustive: T
    get() = this

fun <T> T?.doWhenNull(fn: () -> Unit) = this ?: fn()

inline fun <reified T : Any> simpleName() = T::class.java.simpleName

inline fun <T, R> List<T>.mapToListOf(fn: (T) -> R): List<R> {
    val list = mutableListOf<R>()
    this.forEach {
        list.add(fn(it))
    }
    return list.toList()
}


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


fun <T : Any> FragmentActivity.argumentOrThrow(key: String): Lazy<T> = lazy {
    intent.extras?.get(key) as? T
        ?: throw IllegalStateException("Bundle needs to have argument with id: $key")
}

fun <T : Any> Fragment.argumentOrThrow(key: String): Lazy<T> = lazy {
    arguments?.get(key) as? T
        ?: throw IllegalStateException("Arguments needs to have argument with id: $key")
}

/**
 * layout extensions
 * */
inline fun ViewGroup.inflate(@LayoutRes layout: Int): View {
    return LayoutInflater.from(context).inflate(layout, this, false)
}

/**
 * metric extensions
 * */
fun Int.toPx(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Int.toDp(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()