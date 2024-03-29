package com.alexstibbons.showcase.home.presentation.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import com.alexstibbons.showcase.MediaModel
import com.alexstibbons.showcase.home.databinding.ItemMediaBinding
import com.alexstibbons.showcase.home.presentation.AddRemoveFave
import com.alexstibbons.showcase.search.SearchTerms

internal class FaveRecyclerAdapter(
    override val onMediaClicked: (Int, Int) -> Unit,
    override val addRemoveFave: AddRemoveFave,
    override val isMediaInFaveCache: (Int) -> Boolean
) : RecyclerAdapterBase(onMediaClicked, addRemoveFave, isMediaInFaveCache) {

    private val mediaList: MutableList<MediaModel> = ArrayList()
    private val favesListLocal = mutableListOf<Int>()

    private val faveListener = object: ToggleFaves {
        override fun addFave(media: MediaModel): Boolean {
            favesListLocal.add(media.id)
            addRemoveFave.addFave(media)
            return true
        }
        override fun removeFave(id: Int): Boolean {
            favesListLocal.remove(id)
            addRemoveFave.removeFave(id)
            return true
        }
    }

    private val isMediaFave: (Int) -> Boolean = { mediaId ->
        favesListLocal.contains(mediaId) || isMediaInFaveCache(mediaId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<MediaModel> {
        val binding = ItemMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaViewHolder(binding, onMediaClicked, faveListener, isMediaFave)
    }


    override fun getItemCount(): Int = mediaList.size

    override fun onBindViewHolder(holder: ItemViewHolder<MediaModel>, position: Int) {
        holder.bind(mediaList[position])
    }

    override fun addMedia(newMedia: List<MediaModel>) {
        mediaList.clear()
        mediaList.addAll(newMedia)
        notifyDataSetChanged()
    }

    override fun clearMedia() {
        mediaList.clear()
        favesListLocal.clear()
        notifyDataSetChanged()
    }

    override fun updateFaves(newFaves: List<Int>) {
        favesListLocal.clear()
        favesListLocal.addAll(newFaves)
        notifyDataSetChanged()
    }

    fun filterMediaBy(data: SearchTerms) {

        val base = mediaList.asSequence()
            .filter { it.title.contains(data.title, ignoreCase = true) }
            .toList()

        val filtered = if (data.genreList.isEmpty()) base else base.asSequence()
            .filter { mediaModel -> mediaModel.genreList != null }
            .filter { mediaModel -> mediaModel.genreList!!.any { it in data.genreList } }
            .toList()

        mediaList.clear()
        mediaList.addAll(filtered)

        notifyDataSetChanged()
    }
}