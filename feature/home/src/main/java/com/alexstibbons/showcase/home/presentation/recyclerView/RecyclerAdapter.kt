package com.alexstibbons.showcase.home.presentation.recyclerView

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexstibbons.showcase.MediaModel
import com.alexstibbons.showcase.home.presentation.AddRemoveFave

internal interface ToggleFaves {
    fun addFave(media: MediaModel): Boolean
    fun removeFave(id:Int): Boolean
}

internal class RecyclerAdapter(
    private val onMediaClicked: (Int, Int) -> Unit,
    private val addRemoveFave: AddRemoveFave,
    private val isMediaInFaveCache: (Int) -> Boolean
) : RecyclerView.Adapter<ItemViewHolder<MediaModel>>() {

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

    fun addMedia(newMedia: List<MediaModel>) {
        mediaList.addAll(newMedia)
        notifyDataSetChanged()
    }

    fun removeMedia(title: String) {
    }

    fun clearList() {
        mediaList.clear()
        notifyDataSetChanged()
    }
}