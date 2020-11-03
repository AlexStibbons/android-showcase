package com.alexstibbons.showcase.home.presentation.recyclerView

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexstibbons.showcase.MediaModel

internal class RecyclerAdapter(
    private val onMediaClicked: (String) -> Unit
) : RecyclerView.Adapter<ItemViewHolder<MediaModel>>() {

    private val mediaList: MutableList<MediaModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<MediaModel> = MediaViewHolder(parent, onMediaClicked)

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