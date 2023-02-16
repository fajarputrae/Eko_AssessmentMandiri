package com.example.pilottestmandirieko.di

import com.example.pilottestmandirieko.view.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { MovieViewModel(get()) }
}