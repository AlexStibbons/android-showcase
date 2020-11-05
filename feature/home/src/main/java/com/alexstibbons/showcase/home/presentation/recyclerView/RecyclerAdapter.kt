package com.alexstibbons.showcase.home.presentation.recyclerView

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexstibbons.showcase.MediaModel

internal interface ToggleFaves {
    fun addFave(id: Int): Boolean
    fun removeFave(id:Int): Boolean
}

internal class RecyclerAdapter(
    private val onMediaClicked: (Int, Int) -> Unit
) : RecyclerView.Adapter<ItemViewHolder<MediaModel>>() {

    private val mediaList: MutableList<MediaModel> = ArrayList()
    private val favesList = mutableListOf<Int>()

    private val faveListener = object: ToggleFaves {
        override fun addFave(id: Int) = favesList.add(id)
        override fun removeFave(id: Int) = favesList.remove(id)
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