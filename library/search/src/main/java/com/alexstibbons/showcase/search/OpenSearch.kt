package com.alexstibbons.showcase.search

import com.alexstibbons.showcase.MediaType
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

interface OpenSearch {
    fun newInstance(mediaType: MediaType): BottomSheetDialogFragment
}

class OpenSearchImpl: OpenSearch {
    override fun newInstance(mediaType: MediaType): BottomSheetDialogFragment =
       SearchDialogue.newInstance(mediaType.id)
}