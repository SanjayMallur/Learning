package com.sanjay.learning.details

import com.sanjay.learning.data.entities.PokemonDetails

sealed class PokemonDetailsViewState {
    data class PokemonDetailsSuccess(val pokemonDetails: PokemonDetails) : PokemonDetailsViewState()
   data class PokemonDetailsError(val throwable: Throwable): PokemonDetailsViewState()
}


