package com.example.realestate

import android.app.Application
import com.example.data.di.appModule
import com.example.data.di.dataModule
import com.example.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class RealEstateApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@RealEstateApplication)
            modules(appModule, viewModelModule, dataModule)
        }
    }
}
