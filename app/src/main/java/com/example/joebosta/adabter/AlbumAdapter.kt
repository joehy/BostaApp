package com.example.joebosta.adabter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.joebosta.R
import com.example.joebosta.databinding.AlbumsListItemBinding
import com.example.joebosta.models.Album
import javax.inject.Inject

class AlbumAdapter @Inject constructor() : ListAdapter<Album, AlbumAdapter.ViewHolder>(AsteroidDiffCallback()) {

    private var clickListener: ((Album) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    fun setOnItemClickListener(listener: (Album) -> Unit) {
        clickListener = listener
    }

    class ViewHolder private constructor(private val binding: AlbumsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Album, clickListener: ((Album) -> Unit)?) {
            binding.album = item
            binding.executePendingBindings()
            binding.root.setOnClickListener { clickListener?.invoke(item) }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding = DataBindingUtil.inflate<AlbumsListItemBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.albums_list_item,
                    parent,
                    false
                )
                return ViewHolder(binding)
            }
        }
    }

    class AsteroidDiffCallback : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }
    }
}
