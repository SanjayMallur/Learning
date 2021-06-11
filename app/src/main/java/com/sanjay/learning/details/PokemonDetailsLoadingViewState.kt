package com.sanjay.learning.details

sealed class PokemonDetailsLoadingViewState {
    object Loading : PokemonDetailsLoadingViewState()
    object Loaded : PokemonDetailsLoadingViewState()
    object Error : PokemonDetailsLoadingViewState()
}
