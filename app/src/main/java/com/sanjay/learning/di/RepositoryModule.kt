package com.sanjay.learning.di

import com.sanjay.learning.data.repositories.PokemonDetailsRepository
import com.sanjay.learning.data.repositories.PokemonRepository
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
