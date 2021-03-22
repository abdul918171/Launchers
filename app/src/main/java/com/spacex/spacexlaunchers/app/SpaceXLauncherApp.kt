package com.spacex.spacexlaunchers.app

import android.app.Application
import com.spacex.spacexlaunchers.di.repositoryModule
import com.spacex.spacexlaunchers.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SpaceXLauncherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // starting Koin here in Application class
        startKoin {

            androidContext(this@SpaceXLauncherApp) // providing the app context to Koin

            //now setting all the modules which are going to provide all the dependencies.
            modules(viewModelModule)

            modules(repositoryModule)

        }
    }
}