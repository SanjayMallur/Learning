package com.sanjay.learning.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlin.random.Random


@JsonClass(generateAdapter = true)
data class PokemonDetailsDto(
  @field:Json(name = "id") val id: Int,
  @field:Json(name = "name") val name: String,
  @field:Json(name = "height") val height: Int,
  @field:Json(name = "weight") val weight: Int,
  @field:Json(name = "base_experience") val experience: Int,
  @field:Json(name = "types") val types: List<TypeResponseDto>,
)
@JsonClass(generateAdapter = true)
data class TypeResponseDto(
  @field:Json(name = "slot") val slot: Int,
  @field:Json(name = "type") val type: TypeDto
)

@JsonClass(generateAdapter = true)
data class TypeDto(
  @field:Json(name = "name") val name: String
)
