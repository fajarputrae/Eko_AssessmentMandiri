package com.example.pilottestmandirieko

import android.app.Application
import android.os.StrictMode
import com.example.pilottestmandirieko.di.HelperModule
import com.example.pilottestmandirieko.di.NetworkModule
import com.example.pilottestmandirieko.di.RepositoryModule
import com.example.pilottestmandirieko.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.EmptyLogger
import org.koin.core.logger.Level

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        startKoin()
    }

    private fun startKoin() {
        org.koin.core.context.startKoin {
            if (BuildConfig.DEBUG) androidLogger(Level.ERROR) else EmptyLogger()
            androidContext(this@App)
            modules(
                listOf(
                    HelperModule,
                    NetworkModule,
                    RepositoryModule,
                    ViewModelModule
                )
            )
        }
    }

}