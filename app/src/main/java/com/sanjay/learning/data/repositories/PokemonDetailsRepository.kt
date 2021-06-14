package com.sanjay.learning.data.repositories

import com.sanjay.learning.data.entities.PokemonDetails
import com.sanjay.learning.mappers.PokemonMapper
import com.sanjay.learning.network.PokemonClient
import com.sanjay.learning.data.persistence.PokemonDetailsDao

class PokemonDetailsRepository(
    private val pokemonClient: PokemonClient,
    private val pokemonMapper: PokemonMapper,
    private val pokemonDetailsDao: PokemonDetailsDao
) {

    suspend fun getPokemonDetails(name: String) : PokemonDetails {
        //Check for data in room
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
