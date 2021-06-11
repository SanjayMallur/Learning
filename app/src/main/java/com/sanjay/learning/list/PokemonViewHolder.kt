package com.sanjay.learning.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sanjay.learning.data.Pokemon
import com.sanjay.learning.databinding.PokemonListItemBinding
import com.sanjay.learning.glide.ImageLoader

class PokemonViewHolder(
    private val viewBinding: PokemonListItemBinding,
    private val viewEventListener: ViewEventListener,
    private val imageLoader: ImageLoader,
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun binding(pokemon: Pokemon) {
        viewBinding.pokemonNameTextView.text = pokemon.name
        imageLoader.load(pokemon.getImageUrl()).into(viewBinding.pokemonImageView)

        viewBinding.root.setOnClickListener {
            viewEventListener.onViewEvent(PokeminListViewEvent.OnPokemonClicked(pokemon.name, pokemon.getImageUrl()))
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            viewEventListener: ViewEventListener,
            imageLoader: ImageLoader
        ): PokemonViewHolder {

            val layoutInflater = LayoutInflater.from(parent.context)
            val view = PokemonListItemBinding.inflate(layoutInflater, parent, false)
            return PokemonViewHolder(view, viewEventListener, imageLoader)
        }
    }

}
