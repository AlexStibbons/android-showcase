package com.alexstibbons.showcase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.getSystemService
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.textfield.TextInputEditText

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

fun FragmentActivity.hideKeyboard() {
    val inputMethodManager = getSystemService<InputMethodManager>()

    inputMethodManager?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
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



/**
 * text input extensions
 * */
inline fun TextInputEditText.doAfterTextChange(
    debounceTime: Int = 0,
    crossinline action: (String?) -> Unit
) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(text: Editable?) {
            handler?.postDelayed(debounceTime.toLong()) {
                action(text?.toString())
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            handler?.removeCallbacksAndMessages(null)
        }

    })
}
