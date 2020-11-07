package com.alexstibbons.showcase.home.presentation.recyclerView

import android.view.ViewGroup
import com.alexstibbons.showcase.MediaModel
import com.alexstibbons.showcase.home.presentation.AddRemoveFave

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<MediaModel> = MediaViewHolder(parent, onMediaClicked, faveListener, isMediaFave)

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
}