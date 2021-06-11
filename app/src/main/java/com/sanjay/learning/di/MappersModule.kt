package com.sanjay.learning.di

import com.sanjay.learning.mappers.PokemonMapper
import org.koin.dsl.module

val mappersModule = module {
    factory <PokemonMapper> {
        PokemonMapper()
    }
}
