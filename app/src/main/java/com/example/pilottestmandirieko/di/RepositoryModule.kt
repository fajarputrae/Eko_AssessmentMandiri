package com.example.pilottestmandirieko.di

import com.example.pilottestmandirieko.repository.MovieRepository
import org.koin.dsl.module

val RepositoryModule = module {
    single { MovieRepository(get()) }
}