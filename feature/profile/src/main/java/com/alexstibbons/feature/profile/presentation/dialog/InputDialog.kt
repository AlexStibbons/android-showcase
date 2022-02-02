package com.alexstibbons.feature.profile.presentation.dialog

import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.alexstibbons.feature.profile.databinding.DialogInputBinding
import com.alexstibbons.showcase.argumentOrThrow


internal class InputDialog: DialogFragment() {

    private var _binding : DialogInputBinding? = null
    private val binding get() = _binding ?: error("No binding for dialog found.")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            DialogFragment.STYLE_NORMAL,
            android.R.style.Theme_Light_NoTitleBar_Fullscreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogInputBinding.inflate(inflater, container, false)

        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestFeature(Window.FEATURE_NO_TITLE)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data by argumentOrThrow<InputDialogData>(DIALOG_DATA)

        with (binding) {
            dialogTitle.setText(data.title)
            btnNo.setOnClickListener { dismiss() }
            btnYes.setOnClickListener {
                data.onSaveAction()
                dismiss()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onResume() {
        super.onResume()

        val window = dialog?.window
        val size = Point()

        val display = window?.windowManager?.defaultDisplay
        display?.getSize(size)

        val width: Int = size.x

        window?.let {
            it.setLayout((width * 0.90).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
            it.setGravity(Gravity.CENTER)
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val DIALOG_DATA = "dialog_data"
        const val TAG = "input dialog"
        fun newInstance(data: InputDialogData) =
            InputDialog().apply {
                arguments = bundleOf(
                    DIALOG_DATA to data
                )
            }
    }
}