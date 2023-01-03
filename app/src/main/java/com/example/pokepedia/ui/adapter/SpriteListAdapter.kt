package com.example.pokepedia.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokepedia.databinding.PokemonListItemBinding
import com.example.pokepedia.network.data.Sprite


class SpriteListAdapter(private val onSpriteClicked: (Sprite) -> Unit) :
    ListAdapter<Sprite, SpriteListAdapter.SpriteViewHolder>(DiffCallBack) {

    class SpriteViewHolder(private var binding: PokemonListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(sprite: Sprite) {
            binding.apply {
                pokemonNameHome.text = sprite.name.uppercase()
                imageUri = sprite.sprites.frontDefault
                executePendingBindings()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpriteViewHolder {
        return SpriteViewHolder(
            PokemonListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SpriteViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onSpriteClicked(current)
        }
        holder.bind(current)
    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Sprite>() {
            override fun areItemsTheSame(oldItem: Sprite, newItem: Sprite): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Sprite, newItem: Sprite): Boolean {
                return oldItem == newItem
            }

        }
    }
}