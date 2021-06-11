package com.sanjay.learning.data

import com.sanjay.learning.mappers.PokemonMapper
import com.sanjay.learning.network.PokemonClient
import com.sanjay.learning.paging.Response
import com.sanjay.learning.persistence.PokemonDao

class PokemonRepository(
    private val pokemonClient: PokemonClient,
    private val pokemonMapper: PokemonMapper,
    private val pokemonDao: PokemonDao,
) {
    suspend fun getPokemonList(
        page: Int
    ): Response<List<Pokemon>> {
        //Check for local data before returning results
        val pokemonList = pokemonDao.getPokemonList(page)
        return if (pokemonList.isEmpty()) {
            val pokemonResponseDto = pokemonClient.getPokemonList(page = page)
            val pokemonResponse = pokemonResponseDto.let {
                pokemonMapper.asEntity(it)
            }

            val pokemonResults = pokemonResponse.result
            //map page number to store list to room based on page
            pokemonResults.map { pokemon -> pokemon.page = page }
            //insert result into room
            pokemonDao.insertPokemonList(pokemonResults)
            return Response(
                count = pokemonResponse.count,
                next = pokemonResponse.next,
                previous = pokemonResponse.previous,
                result = pokemonResults
            )
        } else {
            //get all items from room
            Response(
                count = 0,
                next = null,
                previous = null,
                result = pokemonDao.getAllPokemonList()
            )
        }
    }
}
