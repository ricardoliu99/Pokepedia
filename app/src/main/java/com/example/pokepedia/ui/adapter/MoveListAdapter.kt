package com.example.pokepedia.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokepedia.databinding.MoveListItemBinding
import com.example.pokepedia.network.data.Move

class MoveListAdapter() :
    ListAdapter<Move, MoveListAdapter.MoveViewHolder>(DiffCallBack) {

    class MoveViewHolder(private var binding: MoveListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(move: Move) {
            binding.apply {
                name.text = move.name.uppercase()
                accuracy.text = move.accuracy?.toString() ?: "-"
                damageClass.text = move.damageClass.name.uppercase()
                power.text = move.power?.toString() ?: "-"
                pp.text = move.pp.toString()
                priority.text = move.priority.toString()
                type.text = move.type.name.uppercase()
                name.isSelected = true
                accuracy.isSelected = true
                damageClass.isSelected = true
                power.isSelected = true
                pp.isSelected = true
                priority.isSelected = true
                type.isSelected = true
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoveViewHolder {
        return MoveViewHolder(
            MoveListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MoveViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Move>() {
            override fun areItemsTheSame(oldItem: Move, newItem: Move): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Move, newItem: Move): Boolean {
                return oldItem == newItem
            }
        }
    }
}