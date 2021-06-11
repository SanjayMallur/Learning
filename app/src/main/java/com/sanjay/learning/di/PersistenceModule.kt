package com.sanjay.learning.di

import androidx.room.Room
import com.sanjay.learning.persistence.AppDatabase
import com.sanjay.learning.persistence.PokemonDao
import com.sanjay.learning.persistence.PokemonDetailsDao
import com.sanjay.learning.persistence.TypeResponseConverter
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val persistenceModule = module {

    single<AppDatabase> {
        Room.databaseBuilder(androidApplication().applicationContext, AppDatabase::class.java, "Pokemon.db")
            .fallbackToDestructiveMigration()
            .addTypeConverter(get<TypeResponseConverter>())
            .build()
    }

    single<PokemonDao> {
        get<AppDatabase>().pokemonDao()
    }

    single<PokemonDetailsDao> {
        get<AppDatabase>().pokemonDetailsDao()
    }

    factory<TypeResponseConverter> {
        TypeResponseConverter(moshi = get())
    }
}
