package com.sanjay.learning.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sanjay.learning.data.entities.Pokemon
import com.sanjay.learning.data.entities.PokemonDetails

@Database(entities = [Pokemon::class, PokemonDetails::class], version = 1, exportSchema = true)
@TypeConverters(value = [TypeResponseConverter::class])
abstract class AppDatabase : RoomDatabase() {

  abstract fun pokemonDao(): PokemonDao
  abstract fun pokemonDetailsDao(): PokemonDetailsDao
}
