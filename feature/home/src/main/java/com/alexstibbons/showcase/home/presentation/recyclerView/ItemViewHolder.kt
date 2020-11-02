package com.alexstibbons.showcase.home.presentation.recyclerView

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexstibbons.showcase.BASE_IMG_URL
import com.alexstibbons.showcase.home.MediaModel
import com.alexstibbons.showcase.home.R
import com.alexstibbons.showcase.inflate
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_media.view.*

internal abstract class ItemViewHolder<T : MediaModel>(
    parent: ViewGroup
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_media)) {
    abstract fun bind(model: T)
}

internal class MediaViewHolder(
    parent: ViewGroup,
    private val onMediaClicked: (String) -> Unit
) : ItemViewHolder<MediaModel>(parent) {

    private val title = itemView.item_title
    private val image = itemView.item_image
    private val hook = itemView.item_promo

    override fun bind(model: MediaModel) {
        title.text = model.title
        hook.text = model.promo

        Glide.with(itemView.context)
            .asBitmap()
            .load(BASE_IMG_URL + model.imageUrl)
            .circleCrop()
            .into(image)

        itemView.setOnClickListener {
            onMediaClicked(model.title)
        }
    }

}