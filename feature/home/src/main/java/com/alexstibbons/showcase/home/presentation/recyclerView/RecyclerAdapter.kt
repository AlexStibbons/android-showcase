package com.alexstibbons.showcase.home.presentation.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexstibbons.showcase.MediaModel
import com.alexstibbons.showcase.home.databinding.ItemMediaBinding
import com.alexstibbons.showcase.home.presentation.AddRemoveFave

internal interface ToggleFaves {
    fun addFave(media: MediaModel): Boolean
    fun removeFave(id:Int): Boolean
}

internal abstract class RecyclerAdapterBase(
    open val onMediaClicked: (Int, Int) -> Unit,
    open val addRemoveFave: AddRemoveFave,
    open val isMediaInFaveCache: (Int) -> Boolean
) : RecyclerView.Adapter<ItemViewHolder<MediaModel>>() {
    abstract fun addMedia(newMedia: List<MediaModel>)
    abstract fun updateFaves(newFaves: List<Int>)
    abstract fun clearMedia()
}

internal class RecyclerAdapter(
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
        mediaList.addAll(newMedia)
        notifyDataSetChanged()
    }

    override fun clearMedia() {
        mediaList.clear()
        notifyDataSetChanged()
    }

    override fun updateFaves(newFaves: List<Int>) {
        favesListLocal.clear()
        favesListLocal.addAll(newFaves)
        notifyDataSetChanged()
    }
}