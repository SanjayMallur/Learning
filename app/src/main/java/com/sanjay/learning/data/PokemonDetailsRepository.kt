package com.sanjay.learning.data

import com.sanjay.learning.mappers.PokemonMapper
import com.sanjay.learning.network.PokemonClient
import com.sanjay.learning.persistence.PokemonDetailsDao

class PokemonDetailsRepository(
    private val pokemonClient: PokemonClient,
    private val pokemonMapper: PokemonMapper,
    private val pokemonDetailsDao: PokemonDetailsDao
) {

    suspend fun getPokemonDetails(name: String) : PokemonDetails {
        //Check for internet connection
        val pokemonDetailData = pokemonDetailsDao.getPokemonDetails(name)
        return if(pokemonDetailData == null) {
            val pokemonDetails = pokemonClient.getPokemonDetails(name).let {
                pokemonMapper.asEntity(it)
            }
            pokemonDetailsDao.insertPokemonDetails(pokemonDetails)
            pokemonDetails
        }else {
            pokemonDetailData
        }
    }
}
