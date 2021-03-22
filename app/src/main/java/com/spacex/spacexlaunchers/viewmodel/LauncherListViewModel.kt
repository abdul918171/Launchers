package com.spacex.spacexlaunchers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spacex.api.model.Launcher
import com.spacex.spacexlaunchers.repository.LauncherListRepository
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver

class LauncherListViewModel(private val repository: LauncherListRepository,
                            private val observeOnScheduler: Scheduler,
                            private val subscribeOnScheduler: Scheduler,
                            private val compositeDisposable: CompositeDisposable) : ViewModel() {

    private val mtldOnLaunchersListLoaded by lazy {
        MutableLiveData<List<Launcher>>()
    }

    val ldOnLaunchersListLoaded: LiveData<List<Launcher>>
        get() = mtldOnLaunchersListLoaded

    private val mtldOnError by lazy {
        MutableLiveData<String>()
    }

    val ldOnError: LiveData<String>
        get() = mtldOnError

    fun loadLauncherList() {
        compositeDisposable.add(repository.geLauncherList()
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
                .subscribeWith(object : DisposableSingleObserver<List<Launcher>>() {

                    override fun onSuccess(launcherList: List<Launcher>) {
                        mtldOnLaunchersListLoaded.value = launcherList
                    }

                    override fun onError(e: Throwable) {
                        mtldOnError.value = e.message
                    }
                }))
    }

    fun clearError() {
        mtldOnError.value = null
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}