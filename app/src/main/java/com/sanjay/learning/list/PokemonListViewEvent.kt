package com.sanjay.learning.list


sealed class PokemonListViewEvent {
    data class OnPokemonClicked(val name: String, val imageUrl:String): PokemonListViewEvent()
}
