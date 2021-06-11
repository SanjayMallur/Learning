package com.sanjay.learning.paging.di

import com.sanjay.learning.paging.PagerFactory
import org.koin.dsl.module

val pagingModule = module {
    factory<PagerFactory> {
        PagerFactory()
    }
}
