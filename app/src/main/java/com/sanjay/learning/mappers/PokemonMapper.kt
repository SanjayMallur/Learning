package com.sanjay.learning.mappers

import com.sanjay.learning.data.entities.Pokemon
import com.sanjay.learning.data.entities.PokemonDetails
import com.sanjay.learning.data.entities.Type
import com.sanjay.learning.data.entities.TypeResponse
import com.sanjay.learning.network.*
import com.sanjay.learning.paging.Response

/**
 * Mappers helps us to avoid null values from api and show meaningful errors to users
 * */

class PokemonMapper {

    private fun asPokemonEntity(dto: PokemonDto) = dto.run {
        Pokemon(
            name = name,
            url = url
        )
    }

    fun asEntity(dto: PokemonResponseDto) = dto.run {
        Response(
            count = count,
            next = next.orEmpty(),
            previous = previous.orEmpty(),
            result = results.map { asPokemonEntity(it) }
        )
    }

    fun asEntity(dto: PokemonDetailsDto) = dto.run {
        PokemonDetails(
            id = id,
            name = name,
            weight = weight,
            height = height,
            experience = experience,
            types = types.map { asTypeResponseEntity(it) }
        )
    }

    private fun asTypeResponseEntity(dto: TypeResponseDto) = dto.run {
        TypeResponse(slot = slot, type = asTypeEntity(type))
    }

    private fun asTypeEntity(dto: TypeDto) = dto.run {
        Type(name = name)
    }
}
