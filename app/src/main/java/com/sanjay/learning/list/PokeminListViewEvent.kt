package com.sanjay.learning.list


sealed class PokeminListViewEvent {
    data class OnPokemonClicked(val name: String, val imageUrl:String): PokeminListViewEvent()
}
