package com.alexstibbons.feature.profile.presentation.dialog

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
internal class InputDialogData(
    @StringRes val title: Int,
    val onSaveAction: @RawValue () -> Unit
): Parcelable