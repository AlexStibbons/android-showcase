package com.alexstibbons.showcase.search

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.core.content.getSystemService
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.alexstibbons.showcase.MediaType
import com.alexstibbons.showcase.argumentOrThrow
import com.alexstibbons.showcase.doAfterTextChange
import com.alexstibbons.showcase.hideKeyboard
import com.alexstibbons.showcase.navigator.NavigateTo.BundleKeys.MEDIA_TYPE_ID
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialogue_search.*
import kotlin.math.roundToInt


internal class SearchDialogue: BottomSheetDialogFragment(), OnSearchTermsSelectedCallback {

    private var notifySelected: NotifySearchSelected? = null
    private val mediaType: Int by argumentOrThrow(MEDIA_TYPE_ID)

    private var searchTerms = SearchTerms(MediaType.TV)

    companion object {
        fun newInstance(mediaTypeId: Int): SearchDialogue {
            return SearchDialogue().apply { arguments = bundleOf(MEDIA_TYPE_ID to mediaTypeId) }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        notifySelected = context as? NotifySearchSelected

        if (notifySelected == null) {
            notifySelected = parentFragment as? NotifySearchSelected
        }

        if (notifySelected == null) Log.e("Search dialogue", "Search listener not attached to parent")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.SearchDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialogue_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchTerms = searchTerms.copy(mediaType = MediaType.from(mediaType))

        search_title_input.setText(MediaType.from(mediaType).name)

        search_title_input.doAfterTextChange { text ->
            searchTerms = searchTerms.copy(title = text ?: "")
        }

        dialogue_btn_search.setOnClickListener { onSearchDone(searchTerms) }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).also { dialog ->
            dialog.setOnShowListener { dialogInterface ->
                setupFullHeight(dialogInterface as BottomSheetDialog)
            }
        }
    }

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as? FrameLayout ?: return
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)

        val twoThirdsOfScreenHeight = (getWindowHeight() / 1.5).roundToInt()
        bottomSheet.layoutParams = bottomSheet.layoutParams
            .apply { height = twoThirdsOfScreenHeight }

        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (requireContext() as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    override fun onSearchDone(data: SearchTerms) {
        notifySelected?.onSearchTermsFilled(data)

        requireActivity().hideKeyboard()

        dismissAllowingStateLoss()
    }


}

