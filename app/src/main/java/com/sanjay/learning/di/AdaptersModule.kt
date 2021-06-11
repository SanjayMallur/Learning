package com.sanjay.learning.di

import com.sanjay.learning.glide.ImageLoader
import com.sanjay.learning.list.PokemonListFragment
import com.sanjay.learning.list.PokemonListViewModel
import com.sanjay.learning.list.PokemonAdapter
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.dsl.module

val adaptersModule = module {
    factory<PokemonAdapter> { (fragmentPokemon: PokemonListFragment) ->
        val viewModel = fragmentPokemon.getViewModel<PokemonListViewModel>()
        PokemonAdapter(
            viewEventListener = viewModel,
            imageLoader = ImageLoader.with(fragmentPokemon)
        )
    }
}
