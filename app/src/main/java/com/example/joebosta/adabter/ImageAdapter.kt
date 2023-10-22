package com.example.joebosta.adabter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.joebosta.databinding.ImageListItemBinding
import com.example.joebosta.models.Photo
import javax.inject.Inject

class ImageAdapter @Inject constructor(): RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    private var images: List<Photo> = emptyList()
    private var onItemClickListener: ((Photo) -> Unit)? = null

    inner class ViewHolder(private val binding: ImageListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Photo) {
            binding.photo = photo
            binding.root.setOnClickListener { onItemClickListener?.invoke(photo) }
            binding.executePendingBindings()
        }
    }
    fun updateData(newImages: List<Photo>) {
        images = newImages
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ImageListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }



    override fun getItemCount(): Int =differ.currentList.size

    // Set the item click listener
    fun setOnItemClickListener(listener: (Photo) -> Unit) {
        onItemClickListener = listener
    }
    private val differCallback = object : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}
