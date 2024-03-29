package com.alexstibbons.showcase.home.presentation.recyclerView

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.alexstibbons.showcase.*
import com.alexstibbons.showcase.home.R
import com.alexstibbons.showcase.home.databinding.ItemMediaBinding
import com.alexstibbons.showcase.home.domain.MovieDomain
import com.alexstibbons.showcase.home.domain.TvShowDomain
import com.bumptech.glide.Glide

internal abstract class ItemViewHolder<T : MediaModel>(
    binding: ViewBinding,
) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(model: T)
}

internal class MediaViewHolder(
    binding: ItemMediaBinding,
    private val onMediaClicked: (Int, Int) -> Unit,
    private val faveListener: ToggleFaves,
    private val isMediaFave: (Int) -> Boolean
) : ItemViewHolder<MediaModel>(binding) {


    private val title = binding.itemTitle
    private val image = binding.itemImage
    private val hook = binding.itemPromo
    private val genres = binding.itemGenres
    private val faveBtn = binding.btnFave

    override fun bind(model: MediaModel) {

        faveBtn.setOnCheckedChangeListener(null)
        faveBtn.isChecked = false
        if (isMediaFave(model.id)) {
            faveBtn.isChecked = true
        }

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
            .placeholder(R.drawable.ic_logo_big)
            .circleCrop()
            .into(image)

        faveBtn.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                faveListener.addFave(model)
                faveBtn.isChecked = true
            } else {
                faveListener.removeFave(model.id)
                faveBtn.isChecked = false
            }
        }

        itemView.setOnClickListener {
            when (model) {
                is MovieDomain -> onMediaClicked(MediaType.FILM.id, model.id)
                is TvShowDomain -> onMediaClicked(MediaType.TV.id, model.id)
                else -> error("Unknown media")
            }
        }
    }

}