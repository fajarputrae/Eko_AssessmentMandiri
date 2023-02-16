package com.example.pilottestmandirieko.di

import com.example.pilottestmandirieko.helper.UtilityHelper
import org.koin.dsl.module

val HelperModule = module {
    single { UtilityHelper() }
}