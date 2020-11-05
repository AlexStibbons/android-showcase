package com.alexstibbons.showcase.home.presentation.recyclerView

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.alexstibbons.showcase.*
import com.alexstibbons.showcase.home.R
import com.alexstibbons.showcase.home.domain.MovieDomain
import com.alexstibbons.showcase.home.domain.TvShowDomain
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_media.view.*

internal abstract class ItemViewHolder<T : MediaModel>(
    parent: ViewGroup
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_media)) {
    abstract fun bind(model: T)
}

internal class MediaViewHolder(
    parent: ViewGroup,
    private val onMediaClicked: (Int, Int) -> Unit
) : ItemViewHolder<MediaModel>(parent) {

    private val title = itemView.item_title
    private val image = itemView.item_image
    private val hook = itemView.item_promo
    private val genres = itemView.item_genres

    override fun bind(model: MediaModel) {
        title.text = model.title
        hook.text = model.promo
        val genreString: String = model.genreList?.joinToString { it.title } ?: "unknown"

        if (genreString.isNotBlank()) {
            genres.text = "Genres: $genreString"
            genres.isVisible = true
        }

        Glide.with(itemView.context)
            .asBitmap()
            .load(BASE_IMG_URL + model.imageUrl)
            .circleCrop()
            .into(image)

        itemView.setOnClickListener {
            when (model) {
                is MovieDomain -> onMediaClicked(MediaType.FILM.id, model.id)
                is TvShowDomain -> onMediaClicked(MediaType.TV.id, model.id)
                else -> error("Unknown media")
            }
        }
    }

}