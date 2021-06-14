package com.sanjay.learning.list

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.sanjay.learning.data.entities.Pokemon
import com.sanjay.learning.glide.ImageLoader

class PokemonAdapter(
    private val viewEventListener: ViewEventListener,
    private val imageLoader: ImageLoader
) : PagingDataAdapter<Pokemon, PokemonViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        getItem(position)?.let {
            (holder as? PokemonViewHolder)?.binding(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder.create(parent, viewEventListener, imageLoader)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
                oldItem == newItem
        }
    }

}
