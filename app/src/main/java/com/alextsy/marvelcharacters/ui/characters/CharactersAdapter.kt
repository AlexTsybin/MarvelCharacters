package com.alextsy.marvelcharacters.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alextsy.marvelcharacters.R
import com.alextsy.marvelcharacters.data.models.Result
import com.alextsy.marvelcharacters.databinding.ItemCharacterBinding
import com.bumptech.glide.Glide

class CharactersAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<Result, CharactersAdapter.CharacterViewHolder>(CHARACTER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(character: Result) {
            binding.apply {
                Glide.with(itemView)
                    .load("${character.thumbnail.path}/landscape_xlarge.${character.thumbnail.extension}")
                    .into(ivImage)

                tvName.text = character.name
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(character: Result)
    }

    companion object {
        private val CHARACTER_COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(
                oldItem: Result,
                newItem: Result
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Result,
                newItem: Result
            ): Boolean =
                oldItem == newItem
        }
    }
}