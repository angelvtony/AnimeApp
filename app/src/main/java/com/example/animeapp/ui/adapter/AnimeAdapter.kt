package com.example.animeapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animeapp.R
import com.example.animeapp.data.model.Anime
import com.example.animeapp.databinding.ItemAnimeBinding

class AnimeAdapter(private val onClick: (Anime) -> Unit) :
    ListAdapter<Anime, AnimeAdapter.AnimeViewHolder>(AnimeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val binding = ItemAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AnimeViewHolder(
        private val binding: ItemAnimeBinding,
        private val onClick: (Anime) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(anime: Anime) {
            binding.title.text = anime.title
            binding.episodes.text = binding.root.context.getString(R.string.episodes_format, anime.episodes ?: 0)
            binding.rating.text = binding.root.context.getString(R.string.rating_format, anime.score?.toString() ?: "N/A")
            
            Glide.with(binding.root)
                .load(anime.imageUrl)
                .transition(com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(binding.poster)

            binding.root.setOnClickListener { onClick(anime) }
        }
    }

    class AnimeDiffCallback : DiffUtil.ItemCallback<Anime>() {
        override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean = oldItem == newItem
    }
}
