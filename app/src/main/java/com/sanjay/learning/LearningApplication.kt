package com.sanjay.learning

import android.app.Application
import com.sanjay.learning.di.*
import com.sanjay.learning.paging.di.pagingModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class LearningApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(applicationContext)
            koin.loadModules(createKoinModules())
            koin.createRootScope()
        }
    }

    private fun createKoinModules(): List<Module> {
        return listOf(
            networkModule,
            repositoryModule,
            viewModelModule,
            adaptersModule,
            persistenceModule,
            pagingModule,
            mappersModule
        )
    }
}
