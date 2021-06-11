package com.sanjay.learning.di

import com.sanjay.learning.data.PokemonDetailsRepository
import com.sanjay.learning.data.PokemonRepository
import org.koin.dsl.module

val repositoryModule = module {

    factory<PokemonRepository> {
        PokemonRepository(
            pokemonClient = get(),
            pokemonMapper = get(),
            pokemonDao = get()
        )
    }


    factory<PokemonDetailsRepository> {
        PokemonDetailsRepository(
            pokemonClient = get(),
            pokemonMapper = get(),
            pokemonDetailsDao = get()
        )
    }
}
