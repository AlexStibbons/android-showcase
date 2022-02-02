package com.alexstibbons.feature.profile.presentation.view

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import com.alexstibbons.feature.profile.R
import com.alexstibbons.showcase.LoginDataPoint
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.onAfterCredentialsInput
import com.alexstibbons.showcase.validator
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

internal class ExpendableInputCardView (
    context: Context,
    private val inputType: LoginDataPoint,
    attrs: AttributeSet? = null
): LinearLayout(context, attrs)  {

    private var isExpanded = false
    private val input: TextInputEditText by lazy { findViewById(R.id.input_input) }
    private val inputLayout: TextInputLayout by lazy { findViewById(R.id.input_layout) }
    private val titleGhost: View by lazy { findViewById(R.id.title_ghost) }
    private val title: TextView by lazy { findViewById(R.id.card_title) }
    private val saveBtn: MaterialButton by lazy { findViewById(R.id.btn_save) }
    private val inputGroup: Group by lazy { findViewById(R.id.input_group) }
    private val loader: CircularProgressIndicator by lazy { findViewById(R.id.card_loading) }
    var saveInputAction: ((String) -> Unit)? = null

    init {
        inflate(context, R.layout.input_card, this)

        title.setText(inputType.titleText)

        if (inputType == LoginDataPoint.PASSWORD) {
            input.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            inputLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
        }

        inputGroup.isVisible = isExpanded

        saveBtn.isEnabled = false

        input.onAfterCredentialsInput(R.string.error_input, inputType) { text ->
            saveBtn.isEnabled = inputType.validator(text)
        }

        saveBtn.setOnClickListener {
            showLoading()
            saveInputAction?.invoke(input.text.toString())
        }

        titleGhost.setOnClickListener {
            isExpanded = !isExpanded
            inputGroup.isVisible = isExpanded
        }
    }

    fun setSaveAction(action: (String) -> Unit) {
        this.saveInputAction = action
    }

    fun setExistingText(text: String?) {
        text.let { input.setText(text) }
    }

    fun onDone() {
        hideLoading()
        isExpanded = !isExpanded
        inputGroup.isVisible = isExpanded
    }

    private fun showLoading() {
        loader.isVisible = true
    }

    private fun hideLoading() {
        loader.isVisible = false
    }

    private val LoginDataPoint.titleText get() = when (this) {
        LoginDataPoint.EMAIL -> R.string.change_email
        LoginDataPoint.PASSWORD -> R.string.change_pass
    }.exhaustive
}