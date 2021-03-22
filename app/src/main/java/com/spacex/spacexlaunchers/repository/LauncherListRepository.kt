package com.spacex.spacexlaunchers.repository

import com.spacex.api.model.Launcher
import com.spacex.api.ApiService
import io.reactivex.Single


interface LauncherListRepository {
    fun geLauncherList(): Single<List<Launcher>>
}

class LauncherListRepositoryImpl(private val apiService: ApiService) : LauncherListRepository {

    override fun geLauncherList() = apiService.getLauncherList()
}