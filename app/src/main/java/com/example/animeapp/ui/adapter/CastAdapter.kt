package com.example.animeapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animeapp.data.model.AnimeCharacter
import com.example.animeapp.databinding.ItemCastBinding

class CastAdapter : ListAdapter<AnimeCharacter, CastAdapter.CastViewHolder>(CastDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val binding = ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CastViewHolder(private val binding: ItemCastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: AnimeCharacter) {
            binding.characterName.text = character.name
            binding.characterRole.text = character.role
            
            Glide.with(binding.root)
                .load(character.imageUrl)
                .transition(com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade())
                .circleCrop()
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(binding.characterImage)
        }
    }

    class CastDiffCallback : DiffUtil.ItemCallback<AnimeCharacter>() {
        override fun areItemsTheSame(oldItem: AnimeCharacter, newItem: AnimeCharacter): Boolean = oldItem.name == newItem.name
        override fun areContentsTheSame(oldItem: AnimeCharacter, newItem: AnimeCharacter): Boolean = oldItem == newItem
    }
}
