package com.sanjay.learning.di

import com.sanjay.learning.details.PokemonDetailsViewModel
import com.sanjay.learning.list.PokemonListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<PokemonListViewModel> {
        PokemonListViewModel(
            pokemonRepository = get(),
            pagerFactory = get()
        )
    }
    viewModel<PokemonDetailsViewModel> { (name: String) ->
        PokemonDetailsViewModel(pokemonDetailsRepository = get(), name = name)
    }
}
