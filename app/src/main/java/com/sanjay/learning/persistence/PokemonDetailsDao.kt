package com.sanjay.learning.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sanjay.learning.data.PokemonDetails

@Dao
interface PokemonDetailsDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPokemonDetails(pokemonDetails: PokemonDetails)

  @Query("SELECT * FROM PokemonDetails WHERE name = :name_")
  suspend fun getPokemonDetails(name_: String): PokemonDetails?
}
