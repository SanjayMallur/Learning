package com.sanjay.learning.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonDetails(
    @PrimaryKey val id: Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val experience: Int,
    val types: List<TypeResponse>
)

data class TypeResponse(val slot: Int, val type: Type)

data class Type(val name: String)
