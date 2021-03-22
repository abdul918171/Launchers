package com.spacex.spacexlaunchers.di


import com.spacex.api.ApiClient
import com.spacex.spacexlaunchers.repository.LauncherListRepository
import com.spacex.spacexlaunchers.repository.LauncherListRepositoryImpl
import com.spacex.spacexlaunchers.viewmodel.LauncherListViewModel
import com.spacex.spacexlaunchers.viewmodel.SplashViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * This file is responsible for creating and providing all the dependencies required by the app.
 */

val repositoryModule = module {

    /**
     * This single ApiService instance would be shared to all API Repositories listed below
     */
    val apiService by lazy {
        ApiClient.apiService
    }

    single<LauncherListRepository> {
        LauncherListRepositoryImpl(apiService)
    }
}

val viewModelModule = module {

    /**
     * These same instances of the mainScheduler and ioScheduler would be shared to all ViewModels
     * listed below. Also, while creating the instance of 'LauncherListViewModel' note that we have
     * passed the instance of LauncherListRepository using 'get()' as its now Koin's responsibility to
     * get the LauncherListRepository's instance from repositoryModule and pass it here.
     */
    val mainScheduler by lazy {
        AndroidSchedulers.mainThread()
    }

    val ioScheduler by lazy {
        Schedulers.io()
    }

    val computationScheduler by lazy {
        Schedulers.computation()
    }

    val compositeDisposable by lazy {
        CompositeDisposable()
    }

    viewModel {
        SplashViewModel(mainScheduler, computationScheduler)
    }
    viewModel {
        LauncherListViewModel(get(), mainScheduler, ioScheduler, compositeDisposable)
    }
}