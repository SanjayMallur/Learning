package com.sanjay.learning.list

sealed class PokemonSingleViewState {
    data class NavigateToDetails(val name: String, val imageUrl: String) : PokemonSingleViewState()
}
