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
    private val addRemoveFave: AddRemoveFave
) : RecyclerView.Adapter<ItemViewHolder<MediaModel>>() {

    private val mediaList: MutableList<MediaModel> = ArrayList()
    private val favesList = mutableListOf<Int>()

    private val faveListener = object: ToggleFaves {
        override fun addFave(media: MediaModel): Boolean {
            favesList.add(media.id)
            addRemoveFave.addFave(media)
            return true
        }
        override fun removeFave(id: Int): Boolean {
            favesList.remove(id)
            addRemoveFave.removeFave(id)
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<MediaModel> = MediaViewHolder(parent, onMediaClicked, faveListener, favesList)

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