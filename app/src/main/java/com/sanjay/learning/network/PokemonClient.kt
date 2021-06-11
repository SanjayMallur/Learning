package com.sanjay.learning.network

class PokemonClient(
  private val pokemonApi: PokemonApi
) {

  suspend fun getPokemonList(
    page: Int
  ): PokemonResponseDto =
    pokemonApi.getPokemonList(
      offset = page * PAGING_SIZE
    )

  suspend fun getPokemonDetails(
    name: String
  ): PokemonDetailsDto =
    pokemonApi.getPokemonDetails(
      name = name
    )

  companion object {
    private const val PAGING_SIZE = 20
  }
}
